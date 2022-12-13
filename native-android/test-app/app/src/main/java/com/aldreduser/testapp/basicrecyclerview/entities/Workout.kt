package com.aldreduser.testapp.basicrecyclerview.entities

import com.aldreduser.testapp.FIRST_TAB_TITLE

data class Workout (
    var id: Long = 0,
    var workoutName: String = "",
    var workoutGroup: String = FIRST_TAB_TITLE
)