package com.example.catadoption.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catadoption.R
import com.example.catadoption.data.CatRepository
import com.example.catadoption.model.Cat
import com.example.catadoption.model.CatAdapter
import com.example.catadoption.ui.detail.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class Cats : Fragment() {

    private val cats = arrayListOf<Cat>()
    private val catsAdapter = CatAdapter(cats, onClick = {onCatClicked(it)})
    private lateinit var catRepository: CatRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        catRepository = CatRepository(context!!)
        val rootView = inflater.inflate(R.layout.fragment_cats, container, false)
        var video_recyclerview = rootView.findViewById(R.id.rvCats) as RecyclerView // Add


        initViews(video_recyclerview)
        // Inflate the layout for this fragment
        return rootView
    }

    private fun initViews(videoRecyclerview: RecyclerView) {

        videoRecyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        videoRecyclerview.adapter = catsAdapter
        videoRecyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        getRemindersFromDatabase()



    }
    fun refreshFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            var fragmentTransaction: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.detach(Cats())
            fragmentTransaction.attach(Cats());
            fragmentTransaction.commit()
        }
    }

    private fun getRemindersFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val reminders = withContext(Dispatchers.IO) {
                catRepository.getAllReminders()
            }
            cats.clear()
            cats.addAll(reminders)
            catsAdapter.notifyDataSetChanged()
        }
    }

    private fun onCatClicked(movie: Cat) {
        val intent = Intent(activity, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("Cat", movie)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}
