package com.example.testroom.workoutstest1.data

import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout
import kotlinx.coroutines.flow.Flow

class WST1Repo(private val database: WST1Database) {
    // todo: might have to make these vars into functions so they are refreshed every time they are called
    val allWorkoutGroups: Flow<List<WST1Group>> =
        database.groupDao().getAlphabetizedWorkoutGroups()
    //names of Workouts
    val allWorkouts: Flow<List<WST1Workout>> = database.workoutDao().getAlphabetizedWorkouts()
    //names of WorkoutSets
    val allWorkoutSets: Flow<List<WST1Set>> = database.setDao().getAlphabetizedSets()

}