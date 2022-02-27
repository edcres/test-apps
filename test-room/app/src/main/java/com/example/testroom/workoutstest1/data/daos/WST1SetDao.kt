package com.example.testroom.workoutstest1.data.daos

import androidx.room.*
import com.example.testroom.workoutstest1.data.entities.WST1Set
import kotlinx.coroutines.flow.Flow

@Dao
interface WST1SetDao {

    // getting set names alphabetically (ie: "incline bench 1")
    @Query("SELECT * FROM wst1_set_table ORDER BY workout_plus_set ASC")
    fun getAlphabetizedSets(): Flow<List<WST1Set>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(set: WST1Set)

    // Update Item
    @Update
    suspend fun updateSet(set: WST1Set)

    // Delete Item
    @Delete
    suspend fun deleteSet(set: WST1Set)
}