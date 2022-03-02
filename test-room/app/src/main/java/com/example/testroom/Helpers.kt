package com.example.testroom

import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout

const val FIRST_TAB_TITLE = "All Workouts"

fun getGroupWithName(name: String, objects: List<WST1Group>): WST1Group? {
    var group: WST1Group? = null
    objects.forEach {
        if(name == it.groupName) {
            group = it
        }
    }
    return group
}

fun getWorkoutWithName(name: String, objects: List<WST1Workout>): WST1Workout? {
    var workout: WST1Workout? = null
    objects.forEach {
        if(name == it.thisWorkoutName) {
            workout = it
        }
    }
    return workout
}

fun getSetWithName(name: String, objects: List<WST1Set>): WST1Set? {
    var set: WST1Set? = null
    objects.forEach {
        if(name == it.workoutPlusSet) {
            set = it
        }
    }
    return set
}