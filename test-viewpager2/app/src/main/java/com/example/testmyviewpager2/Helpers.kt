package com.example.testmyviewpager2

val testMovieTitles = listOf<String>(
    "Hulk", "Chucky", "Cruella", "Nobody", "Scar Face",
    "Avatar", "Joker", "Profile", "Saw", "Ladies")

val titles = mutableListOf("All Movies")
val titlesList = mutableListOf(Pair("All Movies", 0), Pair("Doctor", 1))

const val MY_LOG = "MY_LOG"

fun pairsToList(titlePairs: MutableList<Pair<String, Int>>): MutableList<String> {
    val newList: MutableList<String> = mutableListOf()
    for(theString in 1..titlePairs.size) {
        val (theTitle, _) = titlePairs[theString]
        newList.add(theTitle)
    }
    return newList
}