package com.example.testwebapis.rndcats.network

import com.squareup.moshi.Json

data class CatPhoto (
    val id: String,

    // To use a different property name for a key, annotate that property with the @Json annotation and the JSON key name.
    @Json(name = "img_src")
    val imgSrcUrl: String
)