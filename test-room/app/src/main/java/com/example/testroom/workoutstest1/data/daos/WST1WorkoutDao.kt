package com.example.testroom.workoutstest1.data.daos

import android.util.Log
import androidx.room.*
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import com.example.testroom.workoutstest1.data.entities.WST1WorkoutAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface WST1WorkoutDao {

    // getting workout names alphabetically
    @Query("SELECT * FROM wst1_workout_table ORDER BY id ASC")
    fun getAlphabetizedWorkouts(): Flow<List<WST1Workout>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: WST1Workout)
    @Update
    suspend fun update(workout: WST1Workout)
    @Delete
    suspend fun delete(workout: WST1Workout)

    // relationship between workout and set
    @Transaction
    @Query("SELECT * FROM wst1_workout_table")
    suspend fun getWorkoutWithSets(): List<WST1WorkoutAndSets>
}