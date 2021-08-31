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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
    }
}
