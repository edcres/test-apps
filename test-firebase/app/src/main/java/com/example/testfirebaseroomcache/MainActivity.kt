package com.example.testfirebaseroomcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import com.example.testfirebaseroomcache.entities.SicknessInfo
import com.example.testfirebaseroomcache.entities.PatientInfo
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    }
}