package com.example.testroom.onetomany.data

import androidx.room.*

@Dao
interface OneToManyDao {

//    @Transaction    // Ensures that the whole operation is performed atomically.
//    @Query("SELECT * FROM")
//    fun getWorkoutsAndGroups(): List<WorkoutAndGroupOneToMany>

//    @Query("SELECT * FROM one_to_many_workout WHERE group_name = :groupName")
//    fun getWorkoutsAndGroups(groupName: String): List<WorkoutAndGroupOneToMany>
}