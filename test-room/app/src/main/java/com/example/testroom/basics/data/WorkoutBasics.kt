package com.example.testroom.basics.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class WorkoutBasics (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "workout")
    val name: String
)