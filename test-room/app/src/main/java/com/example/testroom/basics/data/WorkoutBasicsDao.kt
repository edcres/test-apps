package com.example.testroom.basics.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutBasicsDao {

    @Query("SELECT * FROM workout_table ORDER BY workout ASC")
    fun getAlphabetizedWorkouts(): Flow<List<WorkoutBasics>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutBasics: WorkoutBasics)

    @Query("DELETE FROM workout_table")
    suspend fun deleteAll()
}