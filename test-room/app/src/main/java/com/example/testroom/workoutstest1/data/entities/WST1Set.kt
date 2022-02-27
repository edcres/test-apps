package com.example.testroom.workoutstest1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "wst1_set_table",
    foreignKeys = [
        ForeignKey(
            entity = WST1Workout::class,
            parentColumns = ["this_workout_name"],
            childColumns = ["workout_name"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WST1Set (
    @PrimaryKey
    @ColumnInfo(name = "workout_plus_set")
    val workoutPlusSet: String = "",
    @ColumnInfo(name = "workout_name")   // relation to workout group table
    val workoutName: String = "",
    @ColumnInfo(name = "set")
    val set: Int = 1,
    @ColumnInfo(name = "reps")
    var reps: Int = 0,
    @ColumnInfo(name = "weight")
    var weight: Double = 0.0,
)