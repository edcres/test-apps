package com.example.testwebapis.rndcats.network

import com.squareup.moshi.Json

// todo: probably my main problem is getting this class lined up with the JSON data
data class CatPhoto (
    val id: String,

    // To use a different property name for a key, annotate that property with the @Json annotation and the JSON key name.
//    @Json(name = "img_src")
//    val imgSrcUrl: String,
    @Json(name = "url")
    val imgSrcUrl: String,

//    val breeds: List<Any>,
//    val categories: List<Any>,
//    val width: Int,
//    val height: Int,
)