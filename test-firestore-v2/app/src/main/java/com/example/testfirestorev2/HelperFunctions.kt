package com.example.testfirestorev2

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun generateClientGroupID(): String? {
    val lastGroupAddedField = "last group added"
    val db = Firebase.firestore
    val testHousemateActivity = TestHousemateActivity
    var dbQueryDone = false
    var oldID: String
    var newID: String? = null
    // ie. 00000001asdfg, 00000002fagsd, 00000003sgdfa ...
    // Get the latest groupID from the remote db (ie. 00000001asdfg)
    db.collection(testHousemateActivity.GENERAL_COLLECTION)
        .document(testHousemateActivity.GROUPS_DOC)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val groupIDsDoc = document.data as Map<String, Any>
                oldID = groupIDsDoc[lastGroupAddedField] as String
                newID = add1AndScrambleLetters(oldID)
                // Add the new id to the database as the last id added
                db.collection(testHousemateActivity.GENERAL_COLLECTION)
                    .document(testHousemateActivity.GROUPS_DOC)
                    .update(lastGroupAddedField, newID)
                    .addOnSuccessListener { dbQueryDone = true }
                    .addOnFailureListener { e ->
                        Log.d("DB Query", "Error updating doc", e)
                        dbQueryDone = true
                    }
            } else {
                dbQueryDone = true
            }
        }
        .addOnFailureListener { _ ->
            dbQueryDone = true
        }
    while (!dbQueryDone) { /* wait until db query is completed in a different thread */ }
    return newID
}

fun generateClientID(groupID: String): String {
    val db = Firebase.firestore
    val testHousemateActivity = TestHousemateActivity
    var oldID: String
    // todo
    // get the latest clientID from the group, set 'oldID' to this
    db.collection(testHousemateActivity.GENERAL_COLLECTION)
        .document(testHousemateActivity.GROUPS_DOC)
        .collection(groupID)
        .document(testHousemateActivity.CLIENTS_DOC)
        .get()

    var newClientID = add1AndScrambleLetters(oldID)
    // "$groupID + 00000001asdfg"
    newClientID = "$groupID$newClientID"
    return newClientID
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
