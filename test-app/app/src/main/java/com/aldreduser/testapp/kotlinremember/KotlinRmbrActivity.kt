package com.aldreduser.testapp.kotlinremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aldreduser.testapp.R

class KotlinRmbrActivity : AppCompatActivity() {

    private val TAG = "KotlinRmbrTAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_rmbr)

//        consoleInput()
//        breakTest()
//        continueTest()
//        iterateAString()
//        isValueInArray()
    }

    // check if a value is in a list (also works with custom objects)
    private fun isValueInArray() {
        val numbs = listOf(2,5,18,3,76,13,8,0,23)
        if (76 in numbs) {
            Log.d(TAG, "isValueInRange: true")
        }
    }

    // use a for loop to iterate over characters ofa string
    private fun iterateAString() {
        val name = "James"
        for (char in name) {
            Log.d(TAG, "iterateAString: $char")
        }
    }

    // skip an iteration of a loop
    private fun continueTest() {
        for (i in 1..100) {
            // if i is divisible by 5. skip this iteration
            if (i%5 == 0) {
                Log.d(TAG, "continueTest: skip $i")
                continue
            }
//            val lastDigit = (i).toString().last()
//            if (lastDigit == '0' || lastDigit == '5') {
//                Log.d(TAG, "continueTest: skip $i")
//                continue
//            }
            Log.d(TAG, "continueTest: $i")
        }
    }

    // break a loop or something
    private fun breakTest() {
        for (i in 1..100) {
            Log.d(TAG, "breakTest: $i")
            if (i == 23) {
                Log.d(TAG, "breakTest: break the loop")
                break
            }
        }
    }

    // get user input in the console
    private fun consoleInput() {
        println("Input some text:")
        val inputText = readLine()

        if (inputText != null) {
            println(inputText)
        }
    }
}