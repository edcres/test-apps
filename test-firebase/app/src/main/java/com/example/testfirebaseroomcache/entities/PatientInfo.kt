package com.example.testfirebaseroomcache.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PatientInfo(
    // These variable names become the names of the database nodes.
    var patientName: String? = "",
    var mobile: String? = "",
    var sickness: String? = ""
)