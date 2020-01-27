package com.example.catadoption.ui.main

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.catadoption.R
import com.example.catadoption.data.CatRepository
import com.example.catadoption.model.Cat
import com.example.catadoption.ui.fragments.Cats
import com.example.catadoption.ui.fragments.favs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var reminderRepository: CatRepository
    lateinit var catsList: ArrayList<Cat>
    lateinit var cats: ArrayList<Cat>
    lateinit var cat: Cat

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cats -> {
                    println("home pressed")
                    replaceFragment(Cats())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favs -> {
                    println("map pressed")
                    replaceFragment(favs())
                    return@OnNavigationItemSelectedListener true
                }

            }

            false

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reminderRepository = CatRepository(this)
        initFireStore()

        bottomNavigatoin.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(favs())
    }

    private fun printmycats(list: List<Cat>) {
        for (cat in list)
            println(cat.Cattributes[1].toString())

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun initFireStore() {
        CoroutineScope(Dispatchers.IO).launch{
            reminderRepository.deleteAllCats()
        }
        db.collection("Cats")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    cat = document.toObject(Cat::class.java)
                    saveCat(cat)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun saveCat(cat: Cat) {

            CoroutineScope(Dispatchers.IO).launch {
                reminderRepository.insertCat(cat)
            }
    }
}
