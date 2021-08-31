package com.example.testfirebaseroomcache

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testfirebaseroomcache.basicconcepts.BasicsActivity
import com.example.testfirebaseroomcache.cutomerorders.OrdersActivity
import kotlinx.android.synthetic.main.activity_main.*

// Have a Firebase database with offline cache

// maybe clean up te code in the future, I'm only testing the realtime database
// do it like this instead:
// https://firebase.google.com/docs/database/android/lists-of-data
// also enable offline persistence:
// https://firebase.google.com/docs/database/android/offline-capabilities
// add authentication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonsOnClick()
    }

    private fun buttonsOnClick() {
        orders_db_btn.setOnClickListener {
            val goToOrdersDB = Intent(this, OrdersActivity::class.java)
            startActivity(goToOrdersDB)
        }
        basics_db_btn.setOnClickListener {
            val goToSimpleDB = Intent(this, BasicsActivity::class.java)
            startActivity(goToSimpleDB)
        }
    }
}