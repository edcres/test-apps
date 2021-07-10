package com.example.testfirebaseroomcache.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo(
    var name: String? = "",
    var mobile: String? = "",
    var sickNessLevel: String? = ""
)