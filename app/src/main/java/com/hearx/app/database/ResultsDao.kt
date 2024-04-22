package com.hearx.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResultsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultData: ResultData)

    @Delete
    suspend fun delete(resultData: ResultData)

    @Query("SELECT * from results_table order by score DESC")
    fun getAllResults(): List<ResultData>

    @Query("UPDATE results_table set score = :score, difficulty = :difficulty where id = :id")
    suspend fun update(id: Int?, score: String?, difficulty: String?)
}