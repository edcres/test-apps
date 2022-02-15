package com.example.testroom.onetomany.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SchoolAndDirector(
    @Embedded val school_table: School,
    @Relation(
        parentColumn = "school_name",
        entityColumn = "school_name"
    )
    val director_table: Director
)