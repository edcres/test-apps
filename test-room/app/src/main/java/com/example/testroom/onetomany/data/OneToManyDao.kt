package com.example.testroom.onetomany.data

import androidx.room.*
import com.example.testroom.onetomany.data.entities.Group
import com.example.testroom.onetomany.data.entities.Workout
import com.example.testroom.onetomany.data.entities.GroupAndWorkout
import kotlinx.coroutines.flow.Flow

@Dao
interface OneToManyDao {

    // Returns all instances of the data class that pairs the parent entity and the child entity.
    @Transaction
    @Query("SELECT * FROM group_table")
    fun getGroupsAndWorkouts(): Flow<List<GroupAndWorkout>>

    @Query("DELETE FROM group_table")
    suspend fun deleteAllGroups()

    @Query("DELETE FROM workout_table")
    suspend fun deleteAllWorkouts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @PrimaryKey
    suspend fun insertGroup(workout: Group)//: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(group: Workout)
//    @Transaction    // Ensures that the whole operation is performed atomically.
//    @Query("SELECT * FROM")
//    fun getWorkoutsAndGroups(): List<WorkoutAndGroupOneToMany>

//    @Query("SELECT * FROM one_to_many_workout WHERE group_name = :groupName")
//    fun getWorkoutsAndGroups(groupName: String): List<WorkoutAndGroupOneToMany>
}