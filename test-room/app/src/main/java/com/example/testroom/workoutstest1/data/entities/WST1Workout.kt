package com.example.testroom.workoutstest1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.testroom.FIRST_TAB_TITLE

@Entity(
    tableName = "wst1_workout_table",
    foreignKeys = [
        ForeignKey(
            entity = WST1Group::class,
            parentColumns = ["group_name"],
            childColumns = ["workout_group"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WST1Workout (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "this_workout_name")
    var thisWorkoutName: String = "",
    @ColumnInfo(name = "workout_group")  // relation to workout group table
    var workoutGroup: String = FIRST_TAB_TITLE
//    @ColumnInfo(name = "sets")
//    var sets: Int = 1
)