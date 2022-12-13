package com.example.testroom.onetomany.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class GroupAndWorkout(
    @Embedded val group_table: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "group_id"
    )
    val workout_table: List<Workout>
)