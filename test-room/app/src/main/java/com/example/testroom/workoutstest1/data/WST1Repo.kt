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
    //names of Workouts
    val allWorkouts: Flow<List<WST1Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    //names of WorkoutSets
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
}