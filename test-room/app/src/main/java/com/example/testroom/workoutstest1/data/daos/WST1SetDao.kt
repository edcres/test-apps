package com.example.testroom.workoutstest1.data.daos

import androidx.room.*
import com.example.testroom.workoutstest1.data.entities.WST1Set
import kotlinx.coroutines.flow.Flow

@Dao
interface WST1SetDao {

    // getting set names alphabetically (ie: "incline bench 1")
    @Query("SELECT * FROM wst1_set_table ORDER BY workout_plus_set ASC")
    fun getAlphabetizedSets(): Flow<List<WST1Set>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: WST1Set)
    @Update
    suspend fun update(set: WST1Set)
    @Delete
    suspend fun delete(set: WST1Set)

    // todo: all the sets who have this workoutID,
    //  change the workoutName attribute to this workoutName
    suspend fun updateWorkoutOnSets
}