package com.example.testroom.workoutstest1.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WST1WorkoutGroupAndWorkouts(
    @Embedded val workoutGroup: WST1Group,
    @Relation(
        parentColumn = "group_name",
        entityColumn = "workout_group"
    )
    val workouts: List<WST1Workout>
)