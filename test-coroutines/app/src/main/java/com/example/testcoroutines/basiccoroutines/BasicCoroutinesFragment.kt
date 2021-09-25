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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// https://www.youtube.com/watch?v=F63mhZk-1-Y
// CoroutineScope(IO/Default/Main)

// suspend functions should only be called from coroutine scopes or other suspend function
// scoping is a way to organize coroutines into groupings

class BasicCoroutinesFragment : Fragment() {

    private val TAG = "BasicCoroutinesFgt Tag"
    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"
    private lateinit var parallelTxt: TextView
    private lateinit var parallelBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_basic_coroutines, container, false)
        bindUIWidgets(view)

        parallelBtn.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeAPIRequest()
            }
        }
        return view
    }

    private suspend fun fakeAPIRequest() {
        val result1 = getResult1FromAPI()
        Log.d(TAG, "debug: $result1")
        setTextOnMainThread(result1)
    }

    // have to set the text from the Main thread, not the IO thread
    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            val textToDisplay = "\n${parallelTxt.text} $input"
            parallelTxt.text = textToDisplay
        }
    }

    private suspend fun getResult1FromAPI(): String {
        // 'suspend' marks the function as something tha can be asynchronous
        //  that it can be used in a coroutine
        logThread("getResult1FromAPI")
        delay(1000) // delays the coroutine. As opposed to Thread.sleep() which delays the thread
        return RESULT_1
    }

    private fun logThread(methodName: String) {
        // show what method is being called and what coroutine and thread the method is being executed on
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

    private fun bindUIWidgets(view: View) {
        parallelTxt = view.findViewById(R.id.parallel_txt)
        parallelBtn = view.findViewById(R.id.parallel_btn)
    }
}
