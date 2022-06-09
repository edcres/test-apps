package com.example.testcoroutines.basicasyncawait

import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// https://www.youtube.com/watch?v=HPpiPzwQ_cU

private lateinit var result1: Deferred<String>

fun main() {
    fakeAPIRequest()
}

private fun fakeAPIRequest() {
    println("Called1")
//    println("${result1.await()}")
    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            println("Called2")
        }
        val executionTime = measureTimeMillis {
            result1 = async {
                println("fakeAPIRequest: job1 thread: ${Thread.currentThread().name}")
                getResult1FromAPI()
            }
            val result2: Deferred<String> = async {
                println("fakeAPIRequest: job2 thread: ${Thread.currentThread().name}")
                getResult2FromAPI()
            }

            // calling '.await() gets the result from the function'
            //      'setTextOnMainThread' will not execute until the result is ready
            println("\"fakeAPIRequest Got ${result1}\"")     // Shows 'Active'
            println("Got ${result1.await()}")
            println("\"fakeAPIRequest Got ${result1}\"")     // Shows 'Completed'
            println("Got ${result2.await()}")
        }
        println("debug: total time elapsed: $executionTime")
    }
}

private suspend fun getResult1FromAPI(): String {
    delay(2000)
    return "Result #1"
}

private suspend fun getResult2FromAPI(): String {
    delay(1000)
    return "Result #2"
}
