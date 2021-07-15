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

// todo:
// -get rid of many logs
// -bug:
//      - updating the patient node through the database doesn't update the sickness
//      - clicking a button only retrieves the name, not the sickness or mobile

// I don't think there's much of a difference between create and update
//  -I know there's a specific way to update but it seems redundant

// I'm not using the Sickness node to retrieve data, I'm just setting data
// bug but don't fix: when data in 'sickness' node is updated through the database, 'patients' node is not updated

class MainActivity : AppCompatActivity() {

    // To read or write into your database, you need to get the instance of DatabaseReference.
    private lateinit var dbReference: DatabaseReference
    private var patientId: String = "Placeholder"
    private var previousPatientsKeys = mutableListOf<String>()  //contains the patient id
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

            displayPreviousPatientInfoOnTxtViews(name, mobile, sicknessLevel)
            emptyTheEditTexts()

            // set up previous 4 patients
            // add patients to list
            if (previousPatientsKeys.size <= 4) {
                previousPatientsKeys.add(patientId)
                previousPatientsMap.put(patientId, name)
            } else {
                previousPatientsKeys.removeAt(0)
                previousPatientsKeys.add(patientId)
                previousPatientsMap.put(patientId, name)
            }
            populateButtons()
        }
    }

    private fun updatePatientBtnOnClick() {
        update_patient_btn.setOnClickListener {
            // Create a new user or update existing user
            val name: String = name_edt_text.text.toString()
            val mobile: String = mobile_edt_text.text.toString()
            val sicknessLevel: String = sickness_edt_text.text.toString()

            updatePatientNode(name, mobile, sicknessLevel)
            updateSicknessNode(name, sicknessLevel)
            displayPreviousPatientInfoOnTxtViews(name, mobile, sicknessLevel)
            emptyTheEditTexts()
        }
    }

    // user can only remove a patient if it is in one of the buttons
    private fun removePatientBtnOnClick() {
        remove_patient_btn.setOnClickListener {
            //pass the patientID in th button to removePatient()
            val nameInButton = name_edt_text.text.toString()

            val idToRemove = getIDFromMap(nameInButton)

            if (idToRemove != null) {
                removePatient(idToRemove)
            }
        }
    }

    private fun pastPatientsOnClick() {
        previous_patient_1_btn.setOnClickListener {
            displayPreviousPatientInfoOnEditTexts(previous_patient_1_btn.text.toString())
        }
        previous_patient_2_btn.setOnClickListener {
            displayPreviousPatientInfoOnEditTexts(previous_patient_2_btn.text.toString())
        }
        previous_patient_3_btn.setOnClickListener {
            displayPreviousPatientInfoOnEditTexts(previous_patient_3_btn.text.toString())
        }
        previous_patient_4_btn.setOnClickListener {
            displayPreviousPatientInfoOnEditTexts(previous_patient_4_btn.text.toString())
        }
    }

    // displays the changes made on the LAST PATIENT ADDED
    // listen for changes in the database, use: 'addValueEventListener()'
    private fun addUserChangeListener() {
        // User data change listener
        dbReference.child(PATIENTS_NODE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val patient = dataSnapshot.child(patientId)
                    .getValue(PatientInfo::class.java) ?: return    //if it's null, return
                Log.d(TAG, "onDataChange: $patient")

                // Display newly updated name and email
                displayPreviousPatientInfoOnTxtViews(
                    patient.patientName.toString(),
                    patient.mobile.toString(),
                    patient.sickness.toString()
                )
                // update 'previousPatients' list with the changed patient name in the same location
                Log.d(TAG, "onDataChange: ${previousPatientsMap[patientId]}")
                Log.d(TAG, "onDataChange: ${patient.patientName.toString()}")
                Log.d(TAG, "onDataChange: $previousPatientsKeys")
                previousPatientsMap[patientId] = patient.patientName.toString()
                updateSicknessNode(patient.patientName.toString(), patient.sickness.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // a db `read can be canceled if the client doesn't have permission to read from a Firebase database location.
                Log.d(TAG,"onCancelled: ${error.toException()}")
            }
        })
    }

    // HELPER FUNCTIONS //
    private fun updatePatientNode(name: String, mobile: String, sicknessLevel: String) {
        val thePatientID = getIDFromMap(name)
        // updating the user via child nodes
        if (thePatientID != null) {
            if (!TextUtils.isEmpty(name)) {
                dbReference.child(PATIENTS_NODE).child(thePatientID).child(PATIENT_NAME)
                    .setValue(name)
            }
            if (!TextUtils.isEmpty(mobile)) {
                dbReference.child(PATIENTS_NODE).child(thePatientID).child(MOBILE).setValue(mobile)
            }
            if (!TextUtils.isEmpty(sicknessLevel)) {
                dbReference.child(PATIENTS_NODE).child(thePatientID).child(SICKNESS)
                    .setValue(sicknessLevel)
            }
        } else {
            Log.d(TAG, "ERROR: updatePatient: 'thePatientID' is null")
        }
    }

    fun updateSicknessNode(name: String, sicknessLevel: String) {
        val thePatientID = getIDFromMap(name)
        // the problem is that I'm changing the name in the database and using the original name to SicknessNode
        Log.d(TAG, "updateSicknessNode: $name")
        Log.d(TAG, "updateSicknessNode: $thePatientID")
        if (thePatientID != null) {
            if (!TextUtils.isEmpty(name)) {
                dbReference.child(SICKNESS_NODE).child(thePatientID).child(PATIENT_NAME)
                    .setValue(name)
            }

            if (!TextUtils.isEmpty(sicknessLevel)) {
                dbReference.child(SICKNESS_NODE).child(thePatientID).child(SICKNESS)
                    .setValue(sicknessLevel)
            }
        }
    }

    private fun populateButtons() {
        Log.d(TAG, "populateButtons: ${previousPatientsKeys.size}")
        if (previousPatientsKeys.size >= 1) {
            setPatientNameOnBtn(
                previousPatientsMap[previousPatientsKeys[previousPatientsKeys.size-1]]!!, previous_patient_1_btn)
        }
        if (previousPatientsKeys.size >= 2) {
            setPatientNameOnBtn(
                previousPatientsMap[previousPatientsKeys[previousPatientsKeys.size-2]]!!, previous_patient_2_btn)
        }
        if (previousPatientsKeys.size >= 3) {
            setPatientNameOnBtn(
                previousPatientsMap[previousPatientsKeys[previousPatientsKeys.size-3]]!!, previous_patient_3_btn)
        }
        if (previousPatientsKeys.size >= 4) {
            setPatientNameOnBtn(
                previousPatientsMap[previousPatientsKeys[previousPatientsKeys.size-4]]!!, previous_patient_4_btn)
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

    private fun setPatientNameOnBtn(previousPatientName: String, currentBtn: Button) {
        // get patient name from db
        val thePatientID = getIDFromMap(previousPatientName)

        // todo: I'm getting the name from the database even though I already have the name (leave it like this though)
        if (thePatientID != null) {
            dbReference.child(PATIENTS_NODE).child(thePatientID).child(PATIENT_NAME)
                .get().addOnSuccessListener {
                    currentBtn.text = it.value.toString()
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        } else {
            Log.d(TAG, "ERROR: setPatientNameOnBtn: thePatientID is NULL")
        }
    }

    private fun displayPreviousPatientInfoOnTxtViews(theName: String, theMobile: String, theSickness: String) {
        previous_patient_name.setText(theName).toString()     //Name
        previous_patient_mobile.setText(theMobile).toString()   //Mobile
        previous_patient_sickness.setText(theSickness).toString()   //Sickness
    }

    private fun displayPreviousPatientInfoOnEditTexts(patientName: String) {
        var theSickness: String? = null
        var theMobile: String? = null
        val idToDisplay = getIDFromMap(patientName)
        if (idToDisplay != null) {
            dbReference.child(PATIENTS_NODE).child(idToDisplay).child(SICKNESS)
                .get().addOnSuccessListener {
                    theSickness = it.value.toString()
                }

            dbReference.child(PATIENTS_NODE).child(idToDisplay).child(MOBILE)
                .get().addOnSuccessListener {
                    theMobile = it.value.toString()
                }
        }
        // set it to the texts
        name_edt_text.setText(patientName)
        if (theMobile != null) { mobile_edt_text.setText(theMobile) }
        if (theSickness != null) { sickness_edt_text.setText(theSickness) }
    }

    private fun getIDFromMap (patientName: String): String? {
        var thisPatientID: String? = null
        Log.d(TAG, "getIDFromMap: $previousPatientsMap")
        if (previousPatientsMap.isNotEmpty()) {
            previousPatientsMap.forEach { (k, v) ->
                Log.d(TAG, "key and value $k \t\t\t $v")
                if (patientName == v) {
                    thisPatientID = k
                }
            }
        }
        Log.d(TAG, "thisPatientID = $thisPatientID")
        Log.d(TAG, "patientName = $patientName")
        return thisPatientID
    }

    private fun getItemLocationInList(listItem: String, theList: MutableList<String>): Int? {
        var theLocation: Int = 0
        theList.forEach {
            if (listItem == it) {
                return theLocation
            }
            theLocation ++
        }
        return null
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