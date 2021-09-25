package com.example.testcoroutines.basicasyncawait

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testcoroutines.R
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// This async await file works parallel but it's just simple.

// https://www.youtube.com/watch?v=t-3TOke8tq8

// async .await() work with suspend functions
// should always use async when the coroutine returns a result

// async returns a deferred. This deferred can be used to get the result of the calculation of the network call
// - deferred example: Deferred<String>

// '.await()' will block the current coroutine (in this case)  GlobalScope.async(Dispatchers.IO) {}
//      until the result is available (in this case answer1.await())

class AsyncAwaitFragment : Fragment() {

    private val TAG = "Async Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_async_await, container, false)

        // this example doesn't return anything but this is the better way if it did
        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                // notice 'answer1' and 'answer2' are Deferred<String> values
                val answer1 = async {
                    networkCall1()
                }
                val answer2 = async {
                    networkCall2()
                }
                Log.d(TAG, "Answer 1 is ${answer1.await()}")
                Log.d(TAG, "Answer 2 is ${answer2.await()}")
            }
            Log.d(TAG, "Requests took $time ms")
        }

        return view
    }

    suspend fun networkCall1(): String {
        delay(3000L)
        return "Answer 1"
    }

    suspend fun networkCall2(): String {
        delay(3000L)
        return "Answer 1"
    }
}