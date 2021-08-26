package com.example.testfirestorev2

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun generateClientGroupID(): String? {
    Log.d("Acty", "generateClientGroupID: called")
    val lastGroupAddedField = "last group added"
    val db = Firebase.firestore
    val testHousemateActivity = TestHousemateActivity
    var oldID: String
    var newID: String? = null
    // ie. 00000001asdfg, 00000002fagsd, 00000003sgdfa ...
    // Get the latest groupID from the remote db (ie. 00000001asdfg)
    db.collection(testHousemateActivity.GENERAL_COLLECTION)
        .document(testHousemateActivity.GROUPS_DOC)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val groupIdDoc = document.data as Map<String, Any>
                oldID = groupIdDoc[lastGroupAddedField] as String
                newID = add1AndScrambleLetters(oldID)
                // Add the new id to the database as the last id added
                db.collection(testHousemateActivity.GENERAL_COLLECTION)
                    .document(testHousemateActivity.GROUPS_DOC)
                    .update(lastGroupAddedField, newID)
                    .addOnSuccessListener {
                        Log.d("Acty", "generateClientGroupID: lastGroupAddedField updated")
                    }
                    .addOnFailureListener { e ->
                        Log.d("DB Query", "Error updating group doc", e)
                    }
            }
        }
    return newID
}

fun generateClientID(groupID: String) {
    // todo fix concurrency issues here, put this function in the 'TestHmtActy'
    val lastClientAddedField = "last client added"
    val db = Firebase.firestore
    val testHousemateActivity = TestHousemateActivity
    var oldID: String
    var newID: String? = null
    val clientsDocDb = db.collection(testHousemateActivity.GENERAL_COLLECTION)
        .document(testHousemateActivity.GROUPS_DOC)
        .collection(groupID)
        .document(testHousemateActivity.CLIENTS_DOC)
    // get the latest clientID from the db, set 'oldID' to this
    clientsDocDb.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val clientIdDoc = document.data as Map<String, Any>
                oldID = clientIdDoc[lastClientAddedField] as String
                newID = add1AndScrambleLetters(oldID)
                // Add the new id to the database as the last id added
                clientsDocDb.update(lastClientAddedField, newID)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener { e ->
                        Log.d("DB Query", "Error updating client doc", e)
                    }
            } else {
                // the document will be null for the first member in the group
                //  -there is no client id to get, make a new clientID and add it to the db
                val firstClientID = add1AndScrambleLetters("00000000asdfg")
                val firstDocData = hashMapOf<String, Any>(lastClientAddedField to firstClientID)
                clientsDocDb.set(firstDocData)
                    .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
            }
        }
        .addOnFailureListener {
        }
    // "$groupID + 00000001asdfg"
}

fun add1AndScrambleLetters(oldID: String): String {
    val lettersToScramble = "asdfg"
    val newID: String
    var scrambledLetters: String = ""

    // Add random letters to the String
    for (i in 1..5) {
        scrambledLetters = "$scrambledLetters${lettersToScramble.random()}"
    }

    // Get the numbers (first 8 characters) of the 'oldID' string
    var idPosition = oldID.toCharArray(0, 7).toString().toInt()
    // turn to int and add 1 and make it 8 characters (by filing the first characters with 0s)
    idPosition++
    var idPositionString = idPosition.toString()
    while (idPositionString.length < 8) {
        idPositionString = "0$idPosition"
    }
    newID = "$idPositionString$scrambledLetters"

    return newID
}
