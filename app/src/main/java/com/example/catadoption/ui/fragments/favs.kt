package com.example.catadoption.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catadoption.R
import com.example.catadoption.data.CatRepository
import com.example.catadoption.model.Cat
import com.example.catadoption.model.CatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class favs : Fragment() {
    private val cats = arrayListOf<Cat>()
    private val catsAdapter = CatAdapter(cats, onClick = {onCatClicked(it)})
    private lateinit var catRepository: CatRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        catRepository = CatRepository(context!!)
        val rootView = inflater.inflate(R.layout.fragment_favs, container, false)
        var video_recyclerview = rootView.findViewById(R.id.rvFavs) as RecyclerView // Add


        initViews(video_recyclerview)
        // Inflate the layout for this fragment
        return rootView
    }

    private fun initViews(videoRecyclerview: RecyclerView) {

        videoRecyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        videoRecyclerview.adapter = catsAdapter
        videoRecyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        getFavsFromDatabase()
        createItemTouchHelper().attachToRecyclerView(videoRecyclerview)

    }

    private fun getFavsFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val reminders = withContext(Dispatchers.IO) {
                catRepository.getFavs()
            }
            cats.clear()
            cats.addAll(reminders)
            catsAdapter.notifyDataSetChanged()
        }
    }

    private fun onCatClicked(movie: Cat) {

    }
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminderToDelete = cats[position]
                cats.removeAt(position)
                catsAdapter.notifyDataSetChanged()

                CoroutineScope(Dispatchers.Main).launch {
                    reminderToDelete.isFav = 0
                    catRepository.updateReminder(reminderToDelete)


                }



            }
        }
        return ItemTouchHelper(callback)
    }
}
