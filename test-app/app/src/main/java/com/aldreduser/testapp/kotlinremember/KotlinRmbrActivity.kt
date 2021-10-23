package com.aldreduser.testapp.kotlinremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.convertTo
import com.aldreduser.testapp.R
import java.math.BigDecimal
import kotlin.math.pow

// kotlin features to remember

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
        higherOrderFun()
    }


    // Higher order function (takes another function as its argument)
    // useful when we want to change the behavior of a function
    private fun higherOrderFun() {

        // 'apply' is a higher order function. Has an integer and function 'action' as parameters
        fun apply(x: Int, action: (Int) -> Int): Int {
            return action(x)
        }

        Log.d(TAG, "higherOrderFun: ${apply(4, {x -> x*2}) }")
        Log.d(TAG, "higherOrderFun: ${apply(4, {x -> x/2}) }")

        // IMPORTANT:   filter an list
        val listToFilter = listOf(42, 3, 10, 4, 6, 1)
        // make a list with numbers > 5
        val res = listToFilter.filter({ it > 5 })
        Log.d(TAG, "higherOrderFun: $res")
    }

    // anonymous function
    private fun anonymousFunction() {
        // takes 2 Int inputs and returns their sum
        // 'a' and 'b' are the names of the inputs
//        val sumNumbs: (Int, Int) -> Int = {a, b -> a+b}
        val sumNumbs = {a:Int, b:Int -> a+b}
        val sumOfNumbs = sumNumbs(8, 42)
        Log.d(TAG, "anonymousFunction: sum of numbs = $sumOfNumbs")
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

        val arrayOfInt = listOf(1, 3, 6, 8, 9)
        val (even, odd) = arrayOfInt.partition { it % 2 == 0 }
        arrayOfInt.singleOrNull()

        //name.replaceFirst(
        val updatedSignature = mutableListOf<String>()
        val kfk = doubleArrayOf()
        kfk.sum()
        val sentece = "fndkfnkdf"
        sentece.length
        sentece.toDouble()
        sentece.format()
        kfk[0].toBigDecimal().toDouble().toBigDecimal()
        var numb = 1234
        numb.toDouble().pow(3)
        name.groupBy { }
        name.groupingBy { }
        name.toCharArray().forEach { }
        name.toCharArray().size
        name.toCharArray().joinToString("-")
        name.length
        name.replaceFirstChar { it.uppercase() }
        //name.replaceFirst()
        name.capitalize()
        name.slice(0..2)
        name.split("_").joinToString(" ")
        name.replace("_", "")
        name.lowercase()
        name.uppercase()
        for (char in name) {
            Log.d(TAG, "iterateAString: $char")
        }
    }

}

    // skip an iteration of a loop
    private fun continueTest() {
        val TAG = "KotlinRmbrTAG"
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
        val TAG = "KotlinRmbrTAG"
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
