package com.example.testroom.onetoone.Data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.testroom.onetoone.Data.entities.WorkoutAndGroupOneToOne

@Dao
interface OneToOneDao {

    @Transaction    // Ensures that the whole operation is performed atomically.
    @Query("SELECT * FROM one_to_one_workout")
    fun getWorkoutsAndGroups(): List<WorkoutAndGroupOneToOne>
}