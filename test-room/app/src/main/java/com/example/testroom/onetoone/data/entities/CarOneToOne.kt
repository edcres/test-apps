package com.example.testroom.onetoone.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "one_to_one_car")
data class CarOneToOne (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "car")
    val car: String,

    @ColumnInfo(name = "person_name")
    val personName: String,

    @ColumnInfo(name = "person_id")
    val personId: Long
)