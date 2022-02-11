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





    // todo: update the workout's position up (--)
    // todo: change the position of this item and the one that is being replaced.
    @Query("UPDATE workout_table SET position=:newPosition WHERE position=:pastPosition")
    suspend fun updatePosition(pastPosition: Int, newPosition: Int)

//    // todo: update the workout's position down (++)
//    // todo: change the position of this item and the one that is being replaced.
//    @Query("UPDATE workout_table SET position=:newPosition WHERE position=:pastPosition")
//    suspend fun updatePositionDown(pastPosition: Int, newPosition: Int)


}


















