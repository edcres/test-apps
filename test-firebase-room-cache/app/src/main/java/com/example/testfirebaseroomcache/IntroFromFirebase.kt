package com.example.testfirebaseroomcache

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class IntroFromFirebase {

    companion object {
        private const val TAG = "KotlinActivity"
    }

    // Retrieve an instance of your database using 'Firebase.database' and reference the location you want to write to.
    private fun writeMessageToDatabase() {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        // Make your app data update in realtime: add a ValueEventListener to the reference you just created
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(IntroFromFirebase.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(IntroFromFirebase.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}