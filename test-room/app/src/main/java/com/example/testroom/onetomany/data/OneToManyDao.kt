package com.example.testroom.onetomany.data

import androidx.room.*
import com.example.testroom.onetomany.data.entities.Director
import com.example.testroom.onetomany.data.entities.School
import com.example.testroom.onetomany.data.entities.SchoolAndDirector
import kotlinx.coroutines.flow.Flow

@Dao
interface OneToManyDao {

    @Transaction
    @Query("SELECT * FROM school_table")
    fun getSchoolsAndDirectors(): Flow<List<SchoolAndDirector>>

    @Query("DELETE FROM school_table")
    suspend fun deleteAllSchool()

    @Query("DELETE FROM director_table")
    suspend fun deleteAllDirector()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(school: School)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirector(director: Director)
//    @Transaction    // Ensures that the whole operation is performed atomically.
//    @Query("SELECT * FROM")
//    fun getWorkoutsAndGroups(): List<WorkoutAndGroupOneToMany>

//    @Query("SELECT * FROM one_to_many_workout WHERE group_name = :groupName")
//    fun getWorkoutsAndGroups(groupName: String): List<WorkoutAndGroupOneToMany>
}