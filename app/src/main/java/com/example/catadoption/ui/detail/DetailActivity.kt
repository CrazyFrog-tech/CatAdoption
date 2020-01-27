package com.example.catadoption.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.catadoption.R
import com.example.catadoption.data.CatRepository
import com.example.catadoption.model.Cat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    lateinit var reminderRepository: CatRepository

    private lateinit var viewModel: DetailActivityViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        reminderRepository = CatRepository(this)
        supportActionBar?.title = ""
        initViews()
        initViewModel()
        btnAddToFavs.setOnClickListener{
            addCatToFavs()
        }
    }

    private fun addCatToFavs() {
        var cat = intent.extras?.get("Cat") as Cat
        cat.isFav = 1
        CoroutineScope(Dispatchers.IO
        ).launch {
            viewModel.addCatToFav(cat)

        }

    }
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
    }
    
    


    private fun initViews() {
        val bundle = intent.extras
        if (bundle != null) {
            val cat: Cat = bundle.get("Cat") as Cat
            Glide.with(this).load(cat.Background_url).into(ivBackground)
            Glide.with(this).load(cat.Image_url).into(ivCat)
            tvDetails.text = ("\nName: " + cat.Name
                    + "\n\nCattributes: " + cat.Cattributes
                    + "\n\nDescription: " + cat.Description)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            finish()
            true
        } catch (e: Exception) {
            false
        }
    }
}