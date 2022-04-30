package com.example.testwebapis.rndcats.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory

// TODO: use the API key for authentication
//  'api_key=dc6d62fd-0161-42fd-b9a4-e2b0af9f5079'

private const val BASE_URL = "https://api.thecatapi.com"

// The converter tells Retrofit what to do with the data it gets back from the web service.
//      - here it's to fetch a JSON response from the web service, and return it as a String
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CatsApiService {
    @GET("images/search")
    suspend fun getPhotos(): List<CatPhoto>
}

// Initialize the Retrofit service
// This is a standard Kotlin code pattern to use when creating a service object.
object CatsApi {
    val catsApiService : CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)
    }
}