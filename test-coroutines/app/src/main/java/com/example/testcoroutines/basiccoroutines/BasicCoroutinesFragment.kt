package com.example.testcoroutines.basiccoroutines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.testcoroutines.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// https://www.youtube.com/watch?v=F63mhZk-1-Y
// CoroutineScope(IO/Default/Main)

// suspend functions should only be called from coroutine scopes or other suspend function
// scoping is a way to organize coroutines into groupings

// make a db request, get a result, use that result to make another db request

class BasicCoroutinesFragment : Fragment() {

    private val TAG = "BasicCoroutinesFgt Tag"
    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"
    private lateinit var basicsTxt: TextView
    private lateinit var basicsBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_basic_coroutines, container, false)
        bindUIWidgets(view)

        basicsBtn.setOnClickListener {
            // " CoroutineScope(IO).launch {}" creates a coroutine
            CoroutineScope(Default).launch {
                logThread("onCreateView")
                fakeAPIRequest()
            }
        }
        return view
    }

    // API //
    private suspend fun fakeAPIRequest() {
        val result1 = getResult1FromAPI()
        Log.d(TAG, "debug: $result1")
        setTextOnMainThread(result1)

        // when needing the first result (result1) to use in the second suspend fun
        // it waits until the previous result (result1) is ready when the suspend functions
        // are called within the same coroutines, and with the same suspend fun (fakeAPIRequest)
        val result2 = getResult2FromAPI(result1)
        setTextOnMainThread(result2)
    }
    // API //

    // have to set the text from the Main thread, not the IO thread
    private suspend fun setTextOnMainThread(input: String) {
        // calling withContext in a coroutine switches the thread to the mew one
        // the thread only switches to main while within the curly brackets {}
        withContext(Main) {
            logThread("setTextOnMainThread")
            val textToDisplay = "${basicsTxt.text} $input\n"
            basicsTxt.text = textToDisplay
        }
    }

    private suspend fun getResult1FromAPI(): String {
        // 'suspend' marks the function as something tha can be asynchronous
        //  that it can be used in a coroutine
        logThread("getResult1FromAPI")
        delay(1000) // delays the coroutine. As opposed to Thread.sleep() which delays the thread
        return RESULT_1
    }

    private suspend fun getResult2FromAPI(result1: String): String {
        logThread("getResult2FromAPI")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        // show what method is being called and what coroutine and thread the method is being executed on
        Log.d("TAG", "debug: ${methodName}: ${Thread.currentThread().name}")
//        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

    // Bind Widgets //
    private fun bindUIWidgets(view: View) {
        basicsTxt = view.findViewById(R.id.basics_txt)
        basicsBtn = view.findViewById(R.id.basics_btn)
    }
    // Bind Widgets //
}
