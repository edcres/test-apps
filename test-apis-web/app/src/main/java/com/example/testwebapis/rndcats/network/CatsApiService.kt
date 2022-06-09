package com.example.testwebapis.rndcats.network

import com.example.testwebapis.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Headers

// The Ultimate Retrofit Crash Course - Philipp Lackner
// https://www.youtube.com/watch?v=t6Sql3WMAnk

// @Body annotations provide Object conversion to request body (e.g., JSON, protocol buffers)

/** NOTES:
 *
 * Static vs Dynamic Headers:
 *      - static:       @Headers("User-Agent: codersee-application")
 *                      @GET("users")
 *                      fun getUsersSingleStaticHeader(): Call<List<User>>
 *
 *      - dynamic:      fun getSpaceGifs(@Header(BuildConfig.CATS_API_KEY) apiKey: String)
 */

// TODO: use the API key for authentication (I did not need it for some reason)
//  learn where to insert the key in the URL
//  learn how to insert the key in the URL
//      ("Set your API Key as the x-api-key header on evey request.") ("e.g headers[“x-api-key”] = "ABC123"")
//  https://docs.thecatapi.com/authentication
// BuildConfig.CATS_API_KEY

private const val BASE_URL = "https://api.thecatapi.com"
//private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
//https://api.thecatapi.com/v1/images/search?limit=5&page=10&order=Desc

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
//    @GET("images/search")
    @GET("v1/images/search?limit=10")
    suspend fun getPhotos(): List<CatPhoto>
//    @GET("photos")
//    suspend fun getPhotos(): List<CatPhoto>

    // todo: Maybe the name of the header is related to how the API names their API key header
    @Headers("x-api-key: ${BuildConfig.CATS_API_KEY}")
    @GET("v1/images/search?limit=100&category_ids=2&mime_types=gif")
    suspend fun getSpaceGifs(
//        @Header("x-api-key") apiKey: String
    ): List<CatPhoto>

//    : Response<CatPhoto>
}

// Initialize the Retrofit service
// This is a standard Kotlin code pattern to use when creating a service object.
object CatsApi {
    val catsApiService : CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)
    }
}