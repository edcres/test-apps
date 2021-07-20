package com.example.testfirestore

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// In this codeLab you will build a restaurant recommendation app on Android backed by Cloud Firestore
/*
Concepts:
    -Read and write data to Firestore from an Android app
    -Listen to changes in Firestore data in realtime
    -Use Firebase Authentication and security rules to secure Firestore data
    -Write complex Firestore queries
 */

// avoid arrays in Firestore, use maps instead

// https://firebase.google.com/docs/firestore/manage-data/structure-data?authuser=2

class FromDocumentation : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_documentation_main)
    }

    private fun addingDifferentDataTypes() {
        //You can set a field in your document to a server timestamp which tracks when the server receives the update.
        val docData = hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "capital" to false,
            "numberExample" to 3.14159265,
            "timestamp" to FieldValue.serverTimestamp(),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )

        val nestedData = hashMapOf(
            "a" to 5,
            "b" to true
        )

        docData["objectExample"] = nestedData

        db.collection("data").document("one")
            .set(docData)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        // use 'add()' instead of 'set()' on a document to autogenerate a document id

        // update nested objects, use "dot notation" to reference nested fields within the document when you call update()
        val dataExampleDB = db.collection("data").document("one")
            .update(mapOf(
                "objectExample.b" to false
            ))
    }

    private fun updateDoc() {
        // To update some fields of a document without overwriting the entire document, use the update() method:
        val washingtonRef = db.collection("cities").document("DC")

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("capital", true)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    private fun incrementValue() {
        val washingtonRef = db.collection("cities").document("DC")

        // Atomically increment the population of the city by 50.
        washingtonRef.update("population", FieldValue.increment(50))
    }

    private fun deleteADocument() {
        // To delete a document, use the delete() method
        // Deleting a document does not delete its sub-collections!
        //   -You can still access the sub-collection documents by reference: '/mycoll/mydoc/mysubcoll/mysubdoc'
        db.collection("cities").document("DC")
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    private fun deleteField() {
        // To delete specific fields from a document, use the FieldValue.delete() method when you update a document:
        val docRef = db.collection("cities").document("BJ")

        // Remove the 'capital' field from the document
        val updates = hashMapOf<String, Any>(
            "capital" to FieldValue.delete()
        )

        docRef.update(updates).addOnCompleteListener { }
    }
}


/*
structure data in Firestore:
    Documents
    Multiple collections
    Sub-collections within documents
 */

// Can delete collections, documents, and fields.
// Deleting collections is not recommended for android
//      - to delete an entire collection or subcollection in Cloud Firestore, retrieve all the documents within the collection or subcollection and delete them

/* Atomic operations for reading and writing data: (avoids other user writing over your data)

    Batched Writes: a batched write is a set of write operations on one or more documents.
    (performs queries atomically, either everything happens or nothing happens)

    Transactions: a transaction is a set of read and write operations on one or more documents.
    (reads documents first b4 performing query, makes sure db isn't updated by another user until the transaction is complete)
 */

/*
rules: (transaction refers to both transaction and batched writes)
1- perform reads before writes
2- no side effects (ie. don't increment variables in the middle of you transaction)
3- don't go overboard with the number of docs that are in a single transaction (only what is logically related)
4- transaction will fail if offline
5- can only write up to 500 documents at a time in a transaction
 */
