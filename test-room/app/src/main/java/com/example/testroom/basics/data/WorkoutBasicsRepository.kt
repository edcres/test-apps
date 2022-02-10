package com.example.testroom.basics.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WorkoutBasicsRepository(private val workoutBasicsDao: WorkoutBasicsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWorkouts: Flow<List<WorkoutBasics>> = workoutBasicsDao.getAlphabetizedWorkouts()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: WorkoutBasics) {
        workoutBasicsDao.insert(workout)
    }
}