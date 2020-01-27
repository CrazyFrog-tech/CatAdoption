package com.example.catadoption.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.catadoption.model.Cat

@Dao
interface CatDao {

    @Query("SELECT * FROM catTable")
    suspend fun getAllReminders(): List<Cat>

    @Query("SELECT * FROM catTable WHERE isFavourite = 1")
    suspend fun getFavs(): List<Cat>

    @Insert
    suspend fun insertReminder(reminder: Cat)

    @Delete
     suspend fun deleteReminder(reminder: Cat)

    @Update
    suspend fun updateReminder(reminder: Cat)


    @Query("DELETE FROM catTable")
     suspend fun deleteAllCats()

}
