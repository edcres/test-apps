package com.example.testroom.workoutstest1.data.entities

import androidx.room.Embedded
import androidx.room.Relation

class WST1WorkoutAndSets (
    @Embedded val workout: WST1Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    val workoutSets: List<WST1Set>
)