package com.example.testcoroutines.parallelasyncawait

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.testcoroutines.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureTimeMillis

// https://www.youtube.com/watch?v=HPpiPzwQ_cU

class ParallelAsyncAwait : Fragment() {

    private val TAG = "Parallel TAG"
    private lateinit var parallelBtn: Button
    private lateinit var parallelTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parallel_async_await, container, false)
        bindUIWidgets(view)

        parallelBtn.setOnClickListener {
            parallelTxt.text = "Clicked"
            fakeAPIRequest()
        }

        return view
    }

    private fun fakeAPIRequest() {

        CoroutineScope(IO).launch {
            val executionTime = measureTimeMillis {
                val result1: Deferred<String> = async {
                    Log.d(TAG, "fakeAPIRequest: job1 thread: ${Thread.currentThread().name}")
                    getResult1FromAPI()
                }
                val result2: Deferred<String> = async {
                    Log.d(TAG, "fakeAPIRequest: job2 thread: ${Thread.currentThread().name}")
                    getResult2FromAPI()
                }

                // calling '.await() gets the result from the function'
                //      'setTextOnMainThread' will not execute until the result is ready
                Log.d(TAG, "\"fakeAPIRequest Got ${result1}\"")     // Shows 'Active'
                setTextOnMainThread("Got ${result1.await()}")
                Log.d(TAG, "\"fakeAPIRequest Got ${result1}\"")     // Shows 'Completed'
                setTextOnMainThread("Got ${result2.await()}")
            }
            Log.d(TAG, "debug: total time elapsed: $executionTime")
        }
    }

    private suspend fun setTextOnMainThread(input: String) {
        // the thread only switches to main while within the curly brackets {}
        withContext(Main) {
            Log.d(TAG, "switch to main: ${Thread.currentThread()}")
            // the UI always needs to be updated in the Main thread, or the app will crash
            val textToDisplay = "${parallelTxt.text} $input\n"
            parallelTxt.text = textToDisplay
        }
        Log.d(TAG, "switch back: ${Thread.currentThread()}")
    }

    private suspend fun getResult1FromAPI(): String {
        delay(2000)
        return "Result #1"
    }

    private suspend fun getResult2FromAPI(): String {
        delay(1000)
        return "Result #2"
    }

    // BIND WIDGETS //
    private fun bindUIWidgets(view: View) {
        parallelBtn = view.findViewById(R.id.parallel_btn)
        parallelTxt = view.findViewById(R.id.parallel_txt)
    }
}
