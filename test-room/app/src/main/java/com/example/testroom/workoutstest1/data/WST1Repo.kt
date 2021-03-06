package com.example.testroom.workoutstest1.data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import kotlinx.coroutines.flow.Flow

class WST1Repo(private val database: WST1Database) {

    private val TAG = "Repo TAG"

    val allWorkoutGroups: Flow<List<WST1Group>> = database.groupDao().getAlphabetizedWorkoutGroups()
    val allWorkouts: Flow<List<WST1Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    val allWorkoutSets: Flow<List<WST1Set>> = database.setDao().getAlphabetizedSets()

    @WorkerThread
    suspend fun insert(workoutGroup: WST1Group) {
        database.groupDao().insert(workoutGroup)
    }
    @WorkerThread
    suspend fun insert(workout: WST1Workout): Long {
        return database.workoutDao().insert(workout)
    }
    @WorkerThread
    suspend fun insert(workoutSet: WST1Set) {
        database.setDao().insert(workoutSet)
    }

    @WorkerThread
    suspend fun updateWorkout(workout: WST1Workout) {
        Log.d(TAG, "workout to update: $workout")
        database.workoutDao().update(workout)
    }
    @WorkerThread
    suspend fun updateSet(set: WST1Set) {
        Log.d(TAG, "set to update: $set")
        database.setDao().update(set)
    }
    @WorkerThread
    suspend fun updateSetOnSets(startingSet: Int, setsOfThisWorkout: List<WST1Set>) {
        if(setsOfThisWorkout.isNotEmpty()) {
            Log.d(TAG, "updateSetOnSets: \n.\n.")
            for (i in 1..setsOfThisWorkout.size) {
                Log.d(TAG, "setsOfThisWorkout size = ${setsOfThisWorkout.size}")
                if (startingSet <= setsOfThisWorkout[i-1].set) {
                    Log.d(TAG, "this set: ${setsOfThisWorkout[i-1].set}")
                    database.setDao().updateSetOnSets(setsOfThisWorkout[i-1].set, setsOfThisWorkout[i-1].set-1)
                    Log.d(TAG, "old num = $i, new num = ${i - 1}")
                }
            }
        } else {
            Log.i(TAG, "There are no more sets.")
        }
    }
    @WorkerThread
    suspend fun updateWorkoutOnSets(oldWorkout: String, newWorkout: String) {
        database.setDao().updateWorkoutOnSets(oldWorkout, newWorkout)
    }

    @WorkerThread
    suspend fun deleteGroup(group: WST1Group) {
        Log.d(TAG, "group to delete: $group")
        database.groupDao().delete(group)
    }
    @WorkerThread
    suspend fun deleteWorkout(workout: WST1Workout) {
        Log.d(TAG, "workout to delete: $workout")
        database.workoutDao().delete(workout)
    }
    @WorkerThread
    suspend fun deleteSet(set: WST1Set) {
        Log.d(TAG, "set to delete: $set")
        database.setDao().delete(set)
    }

    @WorkerThread
    suspend fun getWorkoutsOfThisGroup(group: String): List<WST1Workout> {
        return database.workoutDao().getWorkoutsOfThisGroup(group)
    }
    @WorkerThread
    suspend fun getSetsOfWorkout(workoutId: Long): List<WST1Set> {
        return database.setDao().getSetsOfWorkout(workoutId)
    }
    @WorkerThread
    suspend fun getNextSetNum(workoutId: Long): Int {
        return database.setDao().getSetNumList(workoutId).size
    }
    @WorkerThread
    suspend fun groupHasWorkouts(groupName: String): Boolean {
        // If list is empty, returns false, else return true
        return database.workoutDao().getWorkoutsOfThisGroup(groupName).isNotEmpty()
    }
}