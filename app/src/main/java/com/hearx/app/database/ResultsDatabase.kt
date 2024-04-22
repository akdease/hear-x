package com.hearx.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hearx.app.utilities.AppConstants

@Database(entities = [ResultData::class], version = 1)
abstract class ResultsDatabase : RoomDatabase() {
    abstract fun resultsDao(): ResultsDao

    companion object {
        @Volatile
        private var INSTANCE: ResultsDatabase? = null

        fun getDatabase(context: Context): ResultsDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                ResultsDatabase::class.java,
                AppConstants.DATABASE_NAME
            ).build().also { INSTANCE = it }
        }
    }
}