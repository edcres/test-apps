package com.example.testfirestorev2

fun generateClientGroupID(): String {
    var oldID: String
    // todo
    // 00000001asdfg
    // 00000002fagsd
    // 00000003sgdfa
    // .
    // .
    // .

    // todo: get the latest groupID from the remote db (ie. 00000001asdfg)
    // -add +1 on the numbers and scramble the letters (make it the new groupID)
    val newID = add1AndScrambleLetters(oldID)
    return newID
}

fun generateClientID(groupID: String): String {
    var oldID: String
    var newClientID: String
    // todo
    // get the latest clientID from the group, set 'oldID' to this
    newClientID = add1AndScrambleLetters(oldID)
    // "$groupID + 00000001asdfg"
    newClientID = "$groupID$newClientID"
    return newClientID
}

private fun add1AndScrambleLetters(oldID: String): String {
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
