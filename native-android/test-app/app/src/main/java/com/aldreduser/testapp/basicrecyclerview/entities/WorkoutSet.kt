package com.aldreduser.testapp.basicrecyclerview.entities

data class WorkoutSet (
    var id: Long = 0,
    val workoutId: Long,
    val workoutName: String = "",
    val set: Int = 1,
    var reps: Int = 0,
    var weight: Double = 0.0,
)