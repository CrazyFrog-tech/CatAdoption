package com.example.catadoption.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.catadoption.data.CatRepository
import com.example.catadoption.model.Cat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val catRepository = CatRepository(application.applicationContext)


    fun deleteCat(cat: Cat) {
        ioScope.launch {
            catRepository.deleteReminder(cat)
        }
    }

    fun addCatToFav(cat: Cat){
        ioScope.launch {
            catRepository.updateReminder(cat)
        }
    }






}