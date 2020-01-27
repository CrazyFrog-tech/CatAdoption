package com.example.catadoption.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catadoption.model.Cat

@Database(entities = [Cat::class], version = 4, exportSchema = false)
abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun reminderDao(): CatDao

    companion object {
        private const val DATABASE_NAME = "CAT_DATABASE"

        @Volatile
        private var reminderRoomDatabaseInstance: CatRoomDatabase? = null

        fun getDatabase(context: Context): CatRoomDatabase? {
            if (reminderRoomDatabaseInstance == null) {
                synchronized(CatRoomDatabase::class.java) {
                    if (reminderRoomDatabaseInstance == null) {
                        reminderRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            CatRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return reminderRoomDatabaseInstance
        }
    }

}
