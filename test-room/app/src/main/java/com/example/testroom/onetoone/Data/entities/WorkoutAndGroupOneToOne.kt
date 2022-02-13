package com.example.testroom.onetoone.Data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class WorkoutAndGroupOneToOne (
    @Embedded val workout: WorkoutOneToOne,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "group_id"
    )
    val group: GroupOneToOne
)