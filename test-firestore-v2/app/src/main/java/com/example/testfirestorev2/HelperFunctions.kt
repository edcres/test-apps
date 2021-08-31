package com.example.testfirestorev2

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

fun displayToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun add1AndScrambleLetters(oldID: String): String {
    val lettersToScramble = "asdfg"
    val newID: String
    var scrambledLetters = ""
    // Add random letters to the String
    for (i in 1..5) {
        scrambledLetters = "$scrambledLetters${lettersToScramble.random()}"
    }
    // Get the fist 8 digits of 'oldID'
    var oldID8Digits = ""
    for (i in 0..7) {
        oldID8Digits = "$oldID8Digits${oldID[i]}"
    }
    var idPosition = oldID8Digits.toInt()
    // turn to int and add 1 and make it 8 characters (by filing the first characters with 0s)
    idPosition++
    var idPositionString = idPosition.toString()
    while (idPositionString.length < 8) {
        idPositionString = "0$idPositionString"
    }
    newID = "$idPositionString$scrambledLetters"

    return newID
}
// Shared Preferences
@SuppressLint("ApplySharedPref")
fun getDataFromSP(sharedPref: SharedPreferences, theTag: String): String? {
    return sharedPref.getString(theTag, null)
}
@SuppressLint("ApplySharedPref")
fun sendDataToSP(sharedPref: SharedPreferences, theTag: String, theData: String) {
    // groupID or clientID
    val spEditor: SharedPreferences.Editor = sharedPref.edit()
    spEditor.putString(theTag, theData).commit()
}
@SuppressLint("ApplySharedPref")
fun clearSP(sharedPref: SharedPreferences) {
    sharedPref.edit().clear().commit()
}
