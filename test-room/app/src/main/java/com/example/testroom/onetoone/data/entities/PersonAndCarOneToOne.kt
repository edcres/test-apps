package com.example.testroom.onetoone.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PersonAndCarOneToOne (
    @Embedded val personOneToOne: PersonOneToOne,
    @Relation(
        parentColumn = "name",
        entityColumn = "personName"
//        parentColumn = "id",
//        entityColumn = "person_id"
    )
    val carOneToOne: CarOneToOne
)