package com.example.testroom.workoutstest1.data

import androidx.annotation.WorkerThread
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import kotlinx.coroutines.flow.Flow

class WST1Repo(private val database: WST1Database) {
    // todo: might have to make these vars into functions so they
    //  are refreshed every time they are called
    val allWorkoutGroups: Flow<List<WST1Group>> =
        database.groupDao().getAlphabetizedWorkoutGroups()
    //names of Workouts
    val allWorkouts: Flow<List<WST1Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    //names of WorkoutSets
    val allWorkoutSets: Flow<List<WST1Set>> = database.setDao().getAlphabetizedSets()

    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutGroup: WST1Group) {
        database.groupDao().insert(workoutGroup)
    }
    // delete (probably similar to insert)
    // workoutGroup+workout relationship (maybe, idk how this would work)

    // WORKOUT
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: WST1Workout) {
        database.workoutDao().insert(workout)
    }
    // update
    // delete
    // workout+workoutSet relationship (maybe, idk how this would work)

    // WORKOUT SET
    // insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workoutSet: WST1Set) {
        database.setDao().insert(workoutSet)
    }
    // update
    // delete
}