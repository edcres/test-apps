package com.example.testroom.onetoone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//(tableName = "one_to_one_person")
data class PersonOneToOne (

    @PrimaryKey
//    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "id")
//    val id: Long = 0,

    //@ColumnInfo(name = "name")
    val name: String,
)