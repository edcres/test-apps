package com.example.testfirebaseroomcache.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class SicknessInfo(
    var nameOfUser: String? = "",
    var sickNessLevel: String? = ""
)
