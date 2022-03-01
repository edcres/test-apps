package com.example.testroom

import com.example.testroom.workoutstest1.data.entities.WST1Workout

const val FIRST_TAB_TITLE = "All Workouts"

fun findIdWithName(name: String, objects: List<WST1Workout>): WST1Workout? {
    var workout: WST1Workout? = null
    objects.forEach { it ->
        if(name == it.thisWorkoutName) {
            workout = it
        }
    }
    return workout
}