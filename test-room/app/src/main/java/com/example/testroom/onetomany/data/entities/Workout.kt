package com.example.testroom.onetomany.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "workout_name")
    val workoutName: String,
    @ColumnInfo(name = "group_id")
    val groupId: Int     // todo: this might cause a bug when the long can't be turned into an int
)