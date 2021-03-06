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
    suspend fun delete(workout: WorkoutBasics) {
        workoutBasicsDao.delete(workout)
    }
    suspend fun deleteAll() {
        workoutBasicsDao.deleteAll()
    }
    suspend fun updateWorkout(workout: WorkoutBasics) {
        workoutBasicsDao.updateWorkout(workout)
    }
    suspend fun moveItemReplaced(moveUp: Boolean, pastPosition: Int, newPosition: Int) {
//        if (moveUp) {
//            workoutBasicsDao.updatePositionUp(pastPosition, newPosition)
//        } else workoutBasicsDao.updatePositionDown(pastPosition, newPosition)
        workoutBasicsDao.updatePosition(pastPosition, newPosition)
    }
}