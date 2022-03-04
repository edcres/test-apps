package com.example.testroom.workoutstest1.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import kotlinx.coroutines.flow.Flow

class WST1Repo(private val database: WST1Database) {

    private val TAG = "Repo TAG"

    // todo: might have to make these vars into functions so they
    //  are refreshed every time they are called
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
    suspend fun updateWorkoutOnSets(oldWorkout: String, newWorkout: String) {
        Log.d(TAG, "set updated: workout old name = $oldWorkout, new name = $newWorkout")
        database.setDao().updateWorkoutOnSets(oldWorkout, newWorkout)
    }
}