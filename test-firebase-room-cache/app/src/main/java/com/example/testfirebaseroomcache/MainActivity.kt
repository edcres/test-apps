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

// Have a Firebase database with offline cache

/*
App Explanation:
- Have a list of patients at a hospital:
- user can add patients to the db
- patient and sickness nodes are added to db
  - Patient info: Name, age, sickness level (1-3)
- latest patient added is displayed in textViews (ValueEventListener added to that patient)
- 4 buttons display the last 4 patients added
  - user can click on this and update that patient selected
- Patient can be removed
 */

// add authentication
// add delete patient

/* todo:
User presses on a previous patient and can edit them
    - name, mobile, sickness show up in edit texts
Update sickness node when patients is edited
Have the name of users show in the buttons
 */

// I'm not using the Sickness node to retrieve data, I'm just setting data
// bug but don't fix: when data in 'sickness' node is updated through the database, 'patients' node is not updated

class MainActivity : AppCompatActivity() {

    // To read or write into your database, you need to get the instance of DatabaseReference.
    private lateinit var dbReference: DatabaseReference
    private var patientId: String = "Placeholder"
    private var previousPatients = mutableListOf<String>()  //contains the patient id
    private var previousPatientsMap = mutableMapOf<String, String>()  // patientID, patientName

    companion object {
        private const val TAG = "KotlinActivity"
        private const val PATIENTS_NODE = "patients"
        private const val SICKNESS_NODE = "sickness"
        private const val PATIENT_NAME = "patientName"
        private const val MOBILE = "mobile"
        private const val SICKNESS = "sickness"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbReference = Firebase.database.reference
        addUserChangeListener()
        createPatientBtnOnClick()
        updatePatientBtnOnClick()
        removePatientBtnOnClick()
        pastPatientsOnClick()
    }

    // EVENT LISTENERS //
    private fun createPatientBtnOnClick() {
        add_patient_btn.setOnClickListener{
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            // Generates a new key everytime it is called
            patientId = dbReference.child(PATIENTS_NODE).push().key.toString()

            // To write something on the database, use the setValue() method.
            val user = PatientInfo(name, mobile, sicknessLevel)
            val sickness = SicknessInfo(name, sicknessLevel)
            // add 'users' and 'sickness' nodes
            // send the Data classes to make the end nodes in the database
            dbReference.child(PATIENTS_NODE).child(patientId).setValue(user)
            dbReference.child(SICKNESS_NODE).child(patientId).setValue(sickness)
            // to get more specific in the nodes, just keep typing '.child(nameOfChild)'

            displayPreviousPatientInfo(name, mobile, sicknessLevel)
            emptyTheEditTexts()



            // set up previous 4 patients
            // add patients to list
            if (previousPatients.size <= 4) {
                previousPatients.add(patientId)
                previousPatientsMap.put(patientId, name)
            } else {
                previousPatients.removeAt(0)
                previousPatients.add(patientId)
                previousPatientsMap.put(patientId, name)
            }
            // todo: retrieve the last 4 patients added from firebase db, and populate 'previousPatients' (when the app starts up)
            populateButtons()
        }
    }

    // todo: bug, it's updating by getting the patientID
    // to fix this, update it by pressing the previous patient buttons and resetting the patientID to the appropriate one
    private fun updatePatientBtnOnClick() {
        update_patient_btn.setOnClickListener {
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            updatePatient(name, mobile, sicknessLevel)      // here
            updateSickness(name, sicknessLevel)             // and here
            displayPreviousPatientInfo(name, mobile, sicknessLevel)
            emptyTheEditTexts()
        }
    }

    private fun removePatientBtnOnClick() {
        remove_patient_btn.setOnClickListener {
            //pass the patientID in th button to removePatient()
            val nameInButton = remove_patient_btn.text.toString()
            var idToRemove: String? = null

            // get the key using the name
            previousPatientsMap.forEach { (k, v) ->
                if (nameInButton == v) {
                    idToRemove = k
                }
            }
            removePatient(idToRemove!!)
        }
    }

    private fun pastPatientsOnClick() {
        previous_patient_1_btn.setOnClickListener {
            // todo: get the user from the database and display the name on the button
        }
        previous_patient_2_btn.setOnClickListener {
            Log.d(TAG, "pastPatientsOnClick: button 2 clicked")
        }
        previous_patient_3_btn.setOnClickListener {
            Log.d(TAG, "pastPatientsOnClick: button 3 clicked")
        }
        previous_patient_4_btn.setOnClickListener {
            Log.d(TAG, "pastPatientsOnClick: button 4 clicked")
        }
    }

    // displays the changes made on the LAST PATIENT ADDED
    // listen for changes in the database, use: 'addValueEventListener()'
    private fun addUserChangeListener() {
        // User data change listener
        dbReference.child(PATIENTS_NODE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val patient = dataSnapshot.child(patientId).getValue(PatientInfo::class.java) ?: return    //if it's null, return
                Log.d(TAG, "onDataChange: $patient")

                // Display newly updated name and email
                displayPreviousPatientInfo(
                    patient.patientName.toString(),
                    patient.mobile.toString(),
                    patient.sickness.toString()
                )
                updateSickness(patient.patientName.toString(), patient.sickness.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // a db `read can be canceled if the client doesn't have permission to read from a Firebase database location.
                Log.d(TAG,"onCancelled: ${error.toException()}")
            }
        })
    }

    private fun updatePatient(name: String, mobile: String, sicknessLevel: String) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name)) {
            dbReference.child(PATIENTS_NODE).child(patientId).child(PATIENT_NAME).setValue(name)
        }

        if (!TextUtils.isEmpty(mobile))
            dbReference.child(PATIENTS_NODE).child(patientId).child(MOBILE).setValue(mobile)

        if (!TextUtils.isEmpty(sicknessLevel)) {
            dbReference.child(PATIENTS_NODE).child(patientId).child(SICKNESS).setValue(sicknessLevel)
        }
    }

    fun updateSickness(name: String, sicknessLevel: String) {
        if (!TextUtils.isEmpty(name)) {
            dbReference.child(SICKNESS_NODE).child(patientId).child(PATIENT_NAME).setValue(name)
        }

        if (!TextUtils.isEmpty(sicknessLevel)) {
            dbReference.child(SICKNESS_NODE).child(patientId).child(SICKNESS).setValue(sicknessLevel)
        }
    }

    private fun displayPreviousPatientInfo(theName: String, theMobile: String, theSickness: String) {
        previous_patient_name.setText(theName).toString()     //Name
        previous_patient_mobile.setText(theMobile).toString()   //Mobile
        previous_patient_sickness.setText(theSickness).toString()   //Sickness
    }

    // todo: currently, the button has the id as text, make it have the name
    private fun populateButtons() {
        Log.d(TAG, "populateButtons: ${previousPatients.size}")
        if (previousPatients.size >= 1) {
            previous_patient_1_btn.text = previousPatients[0]
        }
        if (previousPatients.size >= 2) {
            previous_patient_2_btn.text = previousPatients[1]
        }
        if (previousPatients.size >= 3) {
            previous_patient_3_btn.text = previousPatients[2]
        }
        if (previousPatients.size == 4) {
            previous_patient_4_btn.text = previousPatients[3]
        }
    }

    private fun emptyTheEditTexts() {
        name_edt_text.setText("")
        mobile_edt_text.setText("")
        sickness_edt_text.setText("")
    }

    // use removeValue() in the location of the data
    private fun removePatient(idToRemove: String) {
        dbReference.child(PATIENTS_NODE).child(idToRemove).removeValue()
        dbReference.child(SICKNESS_NODE).child(idToRemove).removeValue()
    }
}

// When data is read or written, the local version of the data is used first.

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