package com.example.testfirestorev2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyOwnTestActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    private lateinit var theEditText: EditText
    private lateinit var theSendBtn: Button
    private lateinit var theGetBtn: Button
    private lateinit var theDeleteBtn: Button

    companion object {
        private const val TAG = "MyOwnTestActyTAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_own_test)

        bindWidgetIDs()
        buttonClickListeners()
    }

    // todo: delete these
    private fun testSendDb() {
        //collection
        val docOne = hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "capital" to false,
            "numberExample" to 3.14159265,
            "timestamp" to FieldValue.serverTimestamp(),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )
        val docTwo = hashMapOf(
            "stringExample2" to "Goodbye world!",
            "booleanExample2" to false,
            "capital2" to true,
            "numberExample2" to 3.14,
            "timestamp2" to FieldValue.serverTimestamp(),
            "listExample2" to arrayListOf(3, 2, 1),
            "nullExample2" to null
        )
        db.collection("data").document("one")
            .set(docOne)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        db.collection("data").document("two")
            .set(docTwo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        //sub-collection
        val docOneOne = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        db.collection("data").document("one")
            .collection("moreData").document("oneOne")
            .set(docOneOne)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
    private fun testTheDbGet() {
        ///////////////// get data (onetime fetch)
        // get documents, I think it comes back in the form of a hashMap
        // get the data from the document
        Log.d(TAG, "testTheDbGet: called")
        var retrievedDocDataMap: HashMap<String, Any>? = null
        db.collection("data").document("one")
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    retrievedDocDataMap = document.data as HashMap<String, Any>
                    val thePie = retrievedDocDataMap!!["numberExample"]
                    theEditText.setText(thePie.toString())
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        // todo: in the real app, turn it into an object
        val docRef = db.collection("cities").document("BJ")
        docRef.get().addOnSuccessListener { documentSnapshot ->
//            val city = documentSnapshot.toObject<City>()
        }
    }
    private fun testTheDBDeleteDoc() {
        // delete document 'two' in the 'data' collection
        db.collection("data").document("two")
            .delete()
            .addOnSuccessListener { Log.d(TAG, "testTheDBDeleteDoc: Doc successfully deleted") }
            .addOnFailureListener { e -> Log.d(TAG, "Error deleting document", e) }
    }

    // CLICK LISTENERS //
    private fun buttonClickListeners() {
        theSendBtn.setOnClickListener {
            testSendDb()
        }
        theGetBtn.setOnClickListener {
            testTheDbGet()
        }
        theDeleteBtn.setOnClickListener {
            testTheDBDeleteDoc()
        }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        theEditText = findViewById(R.id.editTextTextPersonName)
        theSendBtn = findViewById(R.id.button1)
        theGetBtn = findViewById(R.id.button2)
        theDeleteBtn = findViewById(R.id.button3)
    }
}