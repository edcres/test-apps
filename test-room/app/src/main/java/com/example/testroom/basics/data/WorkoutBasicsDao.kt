package com.example.testroom.basics.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutBasicsDao {

    @Query("SELECT * FROM workout_table ORDER BY position ASC")
    fun getAlphabetizedWorkouts(): Flow<List<WorkoutBasics>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutBasics: WorkoutBasics)

    @Delete
    suspend fun delete(workout: WorkoutBasics)

    @Query("DELETE FROM workout_table")
    suspend fun deleteAll()

    @Update
    suspend fun updateWorkout(workout: WorkoutBasics)

    @Query("UPDATE workout_table SET position=:newPosition WHERE position=:pastPosition")
    suspend fun updatePosition(pastPosition: Int, newPosition: Int)
}


















