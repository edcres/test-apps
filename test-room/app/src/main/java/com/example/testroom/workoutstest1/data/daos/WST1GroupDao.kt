package com.example.testroom.workoutstest1.data.daos

import androidx.room.*
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1WorkoutGroupAndWorkouts
import kotlinx.coroutines.flow.Flow

@Dao
interface WST1GroupDao {

    // getting group names alphabetically
    @Query("SELECT * FROM wst1_workout_group_table ORDER BY group_name ASC")
    fun getAlphabetizedWorkoutGroups(): Flow<List<WST1Group>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutGroup: WST1Group)
    @Delete
    suspend fun delete(workoutGroup: WST1Group)

    // relationship between workoutGroup and workout
    // returns all the instances in which the workoutCategory is the same as the categoryName (ie. all legs with legs, and all chest with chest)
    //  -that way i can access all the workouts that are chest by looking up the group name
    @Transaction
    @Query("SELECT * FROM wst1_workout_group_table")
    suspend fun getWorkoutGroupsWithWorkouts(): List<WST1WorkoutGroupAndWorkouts>
}