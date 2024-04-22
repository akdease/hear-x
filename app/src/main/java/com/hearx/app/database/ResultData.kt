package com.hearx.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "results_table")
data class ResultData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Int
) : Serializable