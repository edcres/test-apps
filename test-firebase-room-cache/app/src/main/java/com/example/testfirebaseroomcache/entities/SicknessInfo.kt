package com.example.testfirebaseroomcache.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class SicknessInfo(
    // These variable names become the names of the database nodes.
    var patientName: String? = "",
    var sickness: String? = ""
)
