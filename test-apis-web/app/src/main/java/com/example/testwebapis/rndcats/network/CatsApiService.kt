package com.example.testwebapis.rndcats.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.thecatapi.com"

// The converter tells Retrofit what to do with the data it gets back from the web service.
//      - here it's to fetch a JSON response from the web service, and return it as a String
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CatsApiService {
    @GET("images/search")
    fun getProperties():
            Call<String>
}

// Initialize the Retrofit service
// This is a standard Kotlin code pattern to use when creating a service object.
object CatsApi {
    val catsApiService : CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)
    }
}