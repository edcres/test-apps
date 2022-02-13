package com.example.testroom.onetoone.Data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "one_to_one_group")
data class GroupOneToOne (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "group")
    val group: String,

    @ColumnInfo(name = "user_id")
    val userId: Long
)