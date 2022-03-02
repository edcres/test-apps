package com.example.testroom.workoutstest1.data.entities

import androidx.room.Embedded
import androidx.room.Relation

// with this setup, when a group is removed, all its workouts are removed
data class WST1WorkoutGroupAndWorkouts(
    @Embedded val workoutGroup: WST1Group,
    @Relation(
        parentColumn = "group_name",
        entityColumn = "workout_group"
    )
    val workouts: List<WST1Workout>
)