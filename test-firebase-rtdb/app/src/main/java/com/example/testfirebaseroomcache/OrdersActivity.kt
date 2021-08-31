package com.example.testfirebaseroomcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// save the store# in shared preferences
// save date added
// save order# ( "$storeNumber" + "<5moreNumbersInOrder>" )
// click on order to retrieve it
//  -delete option
// preview displays: order# || short note about the order || date added

class OrdersActivity : AppCompatActivity() {

    val xAxis = listOf<Int>(1,2,3,4,5)
    val yAxis = listOf<List<Int>>(listOf(1,2,3,4), listOf(3,3,3,3))
//    val yAxis = listOf<List<Int>, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
    }
}