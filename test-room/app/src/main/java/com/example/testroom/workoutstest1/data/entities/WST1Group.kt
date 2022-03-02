package com.example.testroom.workoutstest1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// with this setup, when a group is removed, all its workouts are removed
@Entity(tableName = "wst1_workout_group_table")
data class WST1Group (
    @PrimaryKey
    @ColumnInfo(name = "group_name")  // the WorkoutGroup will be identified by the group name
    val groupName: String = "",            // chest, legs, back

//    @ColumnInfo(name = "workoutName")
//    val workoutName: String = ""
)