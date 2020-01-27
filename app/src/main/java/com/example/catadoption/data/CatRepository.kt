package com.example.catadoption.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.catadoption.model.Cat

class CatRepository(context: Context) {

    private var reminderDao: CatDao

    init {
        val reminderRoomDatabase = CatRoomDatabase.getDatabase(context)
        reminderDao = reminderRoomDatabase!!.reminderDao()
    }

    suspend fun getAllReminders(): List<Cat> {

        var cats = reminderDao.getAllReminders()
        return cats
    }

    suspend fun getFavs(): List<Cat> {

        var cats = reminderDao.getFavs()
        return cats
    }

    suspend fun insertCat(reminder: Cat) {
        reminderDao.insertReminder(reminder)
    }

     suspend fun deleteReminder(reminder: Cat) {
        reminderDao.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder: Cat) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteAllCats(){
        reminderDao.deleteAllCats()
    }

}