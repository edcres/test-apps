package com.example.testfirebaseroomcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.testfirebaseroomcache.entities.SicknessInfo
import com.example.testfirebaseroomcache.entities.UserInfo
import com.google.firebase.database.*
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
//  -update button didn't work

/* todo:
Have a list of patients at a hospital:
Patient info: Name, age, sickness level (1-3)
Can create a new patient with their info
Can update a patient's info
Can delete a patient from the record
Have the FireBase database private from the firebase website
 */

// I'm not using the Sickness node to retrieve data, I'm just setting data

class MainActivity : AppCompatActivity() {

    // To read or write into your database, you need to get the instance of DatabaseReference.
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbReference: DatabaseReference
    private var userId: String = "Placeholder"

    companion object {
        private const val TAG = "KotlinActivity"
        private const val USERS_NODE = "users"
        private const val SICKNESS_NODE = "sickness level"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDatabase()
        createUserBtnOnClick()
    }

    private fun setUpDatabase() {
        //get reference to the "users" node
        firebaseDatabase = FirebaseDatabase.getInstance()
        dbReference = firebaseDatabase.reference
    }

    private fun createUserBtnOnClick() {
        create_user_btn.setOnClickListener{
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            // Generates a new key everytime it is called
            userId = dbReference.child(USERS_NODE).push().key.toString()

            // If user (smartphone) id exists then update user info, else create user
//            if(TextUtils.isEmpty(userId)){
                createEntity(name, mobile, sicknessLevel)
//            } else {
                Log.d(TAG, "ERROR CREATING ENTITY")
//            }
        }
    }

    private fun updateUserBtnOnClick() {
        update_user_btn.setOnClickListener {
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

//            if(!TextUtils.isEmpty(userId)){
                updateEntity(name, mobile, sicknessLevel)
//            } else {
                Log.d(TAG, "ERROR CREATING ENTITY")
//            }
        }
    }


        // To write something on the database, use the setValue() method.
    private fun createEntity(name: String, mobile: String, sicknessLevel: String) {
        // Change the info of the current user in the "users" node
        val user = UserInfo(name, mobile, sicknessLevel)
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
            dbReference.child(USERS_NODE).child(userId).child("name").setValue(name)
            dbReference.child(SICKNESS_NODE).child(userId).child("name").setValue(name)
        }

        if (!TextUtils.isEmpty(mobile))
            dbReference.child(USERS_NODE).child(userId).child("mobile").setValue(mobile)

        if (!TextUtils.isEmpty(sicknessLevel)) {
            dbReference.child(USERS_NODE).child(userId).child("sickness").setValue(sicknessLevel)
            dbReference.child(SICKNESS_NODE).child(userId).child("sickness").setValue(sicknessLevel)
        }

        addUserChangeListener()
    }

    // listen for changes in the database, use: 'addValueEventListener()'
    private fun addUserChangeListener() {
        // User data change listener
        dbReference.child(USERS_NODE).child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(UserInfo::class.java) ?: return    // if it's null, return
                val sickness = dataSnapshot.getValue(SicknessInfo::class.java) ?: return    // if it's null, return

                // Display newly updated name and email
                previous_user_name.setText(user.name).toString()     //userNameTv
                previous_user_mobile.setText(user.mobile).toString()   //userMobileTv
                previous_user_sickness.setText(user.sickNessLevel).toString()   //userMobileTv

                // clear edit text
                name_edt_text.setText("")
                mobile_edt_text.setText("")
                sickness_edt_text.setText("")

//                changeButtonText()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: Failed to read value.")
            }
        })
    }

//    private fun changeButtonText(){
//        if (TextUtils.isEmpty(userId)) {
//            // if String is null or zero length
//            update_user_btn.text = "Save";
//        } else {
//            update_user_btn.text = "Update";
//        }
//    }

    // todo: choose from the last 4 entries and delete one
    // To delete a data from a specific location, you can use the removeValue() method on a
    // reference to the location of that data.

}

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