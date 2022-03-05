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

    @Query(
        "UPDATE wst1_set_table " +
                "SET workout_name = :newWorkout " +
                "WHERE workout_name = :oldWorkout"
    )
    suspend fun updateWorkoutOnSets(oldWorkout: String, newWorkout: String)

    @Query(
        "UPDATE wst1_set_table " +
                "SET `set` = :newSetNum " +
                "WHERE `set` = :oldSetNum"
    )
    suspend fun updateSetOnSets(oldSetNum: Int, newSetNum: Int)

    @Query(
        "SELECT * FROM wst1_set_table " +
                "WHERE workout_id = :workoutId " +
                "ORDER BY `set` ASC"
    )
    suspend fun getSetsOfWorkout(workoutId: Long): List<WST1Set>

    @Query(
        "SELECT `set` FROM wst1_set_table " +
                "WHERE workout_id = :workoutId " +
                "ORDER BY `set` ASC"
    )
    suspend fun getSetNumList(workoutId: Long): List<Int>

//    @Update
//    suspend fun updateSets(sets: WST1Set)
}