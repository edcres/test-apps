package com.example.testfirebaseroomcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.testfirebaseroomcache.entities.SicknessInfo
import com.example.testfirebaseroomcache.entities.PatientInfo
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

// Have an Firebase database with offline cache

/*
we are updating and showing the information of the user i.e. name and mobile. So, we will have two
TextViews for showing details and two EditTexts for getting the new values from the user and an update button.
 */

// add authentication
// figure out how to give different users different IDs
// add delete functionality

// todo: bugs
//  -textViews are not updated with the recently added entity
//  -retrieve patient

/* todo:
Have a list of patients at a hospital:
Patient info: Name, age, sickness level (1-3)
Can create a new patient with their info
Can update a patient's info
Can delete a patient from the record
Have the FireBase database private from the firebase website
Have a list of previous names
 */

// I'm not using the Sickness node to retrieve data, I'm just setting data

class MainActivity : AppCompatActivity() {

    // To read or write into your database, you need to get the instance of DatabaseReference.
    private lateinit var dbReference: DatabaseReference
    private var userId: String = "Placeholder"

    companion object {
        private const val TAG = "KotlinActivity"
        private const val USERS_NODE = "users"
        private const val SICKNESS_NODE = "sickness"
        private const val PATIENT_NAME = "patientName"
        private const val MOBILE = "mobile"
        private const val SICKNESS = "sickness"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDatabase()
        createUserBtnOnClick()
        updateUserBtnOnClick()
    }

    private fun setUpDatabase() {
        //get reference to the "users" node
        dbReference = Firebase.database.reference
    }

    private fun createUserBtnOnClick() {
        create_patient_btn.setOnClickListener{
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            // Generates a new key everytime it is called
            userId = dbReference.child(USERS_NODE).push().key.toString()

            createEntity(name, mobile, sicknessLevel)
            Log.d(TAG, "ERROR CREATING ENTITY")

            name_edt_text.setText("")
            mobile_edt_text.setText("")
            sickness_edt_text.setText("")
        }
    }

    private fun updateUserBtnOnClick() {
        update_patient_btn.setOnClickListener {
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            updateEntity(name, mobile, sicknessLevel)
            Log.d(TAG, "ERROR CREATING ENTITY")
        }
    }

        // To write something on the database, use the setValue() method.
    private fun createEntity(name: String, mobile: String, sicknessLevel: String) {
        // Change the info of the current user in the "users" node
        val user = PatientInfo(name, mobile, sicknessLevel)
        val sickness = SicknessInfo(name, sicknessLevel)

        // add 'users' and 'sickness' nodes
        // send the Data classes to make the end nodes in the database
        dbReference.child(USERS_NODE).child(userId).setValue(user)
        dbReference.child(SICKNESS_NODE).child(userId).setValue(sickness)

        // to get more specific in the nodes, just keep typing '.child(nameOfChild)'
    }

    private fun updateEntity(name: String, mobile: String, sicknessLevel: String) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name)) {
            dbReference.child(USERS_NODE).child(userId).child(PATIENT_NAME).setValue(name)
            dbReference.child(SICKNESS_NODE).child(userId).child(PATIENT_NAME).setValue(name)
        }

        if (!TextUtils.isEmpty(mobile))
            dbReference.child(USERS_NODE).child(userId).child(MOBILE).setValue(mobile)

        if (!TextUtils.isEmpty(sicknessLevel)) {
            dbReference.child(USERS_NODE).child(userId).child(SICKNESS).setValue(sicknessLevel)
            dbReference.child(SICKNESS_NODE).child(userId).child(SICKNESS).setValue(sicknessLevel)
        }

        addUserChangeListener()
    }

    // listen for changes in the database, use: 'addValueEventListener()'
    private fun addUserChangeListener() {
        // User data change listener
        dbReference.child(USERS_NODE).child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(PatientInfo::class.java) ?: return    // if it's null, return
                val sickness = dataSnapshot.getValue(SicknessInfo::class.java) ?: return    // if it's null, return

                // Display newly updated name and email
                previous_patient_name.setText(user.patientName).toString()     //userNameTv
                previous_patient_mobile.setText(user.mobile).toString()   //userMobileTv
                previous_patient_sickness.setText(user.sickness).toString()   //userSicknessTv

//                // clear edit text
//                name_edt_text.setText("")
//                mobile_edt_text.setText("")
//                sickness_edt_text.setText("")

//                changeButtonText()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: Failed to read value.")
            }
        })
    }

    // todo: choose from the last 4 entries and delete one
    // To delete a data from a specific location, you can use the removeValue() method on a
    // reference to the location of that data.

}

// JSON value types: String, Long, Double, Boolean, Map<String, Object>, List<Object>
//  -can also pass custom Java objects (ie. Patient(name, mobile))

/* Firebase recommended way to Structure Your Database.
// An index to track Ada's memberships
{
  "users": {
    "alovelace": {
      "name": "Ada Lovelace",
      // Index Ada's groups in her profile
      "groups": {
         // the value here doesn't matter, just that the key exists
         "techpioneers": true,
         "womentechmakers": true
      }
    },
    ...
  },
  "groups": {
    "techpioneers": {
      "name": "Historical Tech Pioneers",
      "members": {
        "alovelace": true,
        "ghopper": true,
        "eclarke": true
      }
    },
    ...
  }
}

You might notice that this duplicates some data by storing the relationship under both Ada's record
 and under the group. Now alovelace is indexed under a group, and techpioneers is listed in Ada's
 profile. So to delete Ada from the group, it has to be updated in two places.


 Check for a key by reading: /users/$uid/groups/$group_id  and check if it's null.
 */