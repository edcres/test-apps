package com.example.testroom.workoutstest1.data.entities

import androidx.room.Embedded
import androidx.room.Relation

class WST1WorkoutAndSets (
    @Embedded val workout: WST1Workout,
    @Relation(
        parentColumn = "this_workout_name",
        entityColumn = "workout_name"
    )
    val workoutSets: List<WST1Set>
)