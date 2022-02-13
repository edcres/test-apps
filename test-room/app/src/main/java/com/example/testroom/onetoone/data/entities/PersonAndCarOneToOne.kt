package com.example.testroom.onetoone.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PersonAndCarOneToOne (
    @Embedded val person: PersonOneToOne,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "car_id"
    )
    val car: CarOneToOne
)