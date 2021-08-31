package com.example.testfirebaseroomcache.cutomerorders

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CustomerOrder (
    var number: String? = "",
    var details: String? = "",
    var note: String? = "",
    var dateAdded: String? = ""
)