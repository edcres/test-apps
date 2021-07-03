package com.example.testmyviewpager2

// pre-defined list of titles
val testMovieTitles = listOf(
    "Hulk", "Chucky", "Cruella", "Nobody", "Scar Face",
    "Avatar", "Joker", "Profile", "Saw", "Ladies")

val titles = mutableListOf("All Movies")
val titlesOrdinals: MutableMap<String, Int> = mutableMapOf("All Movies" to 0)   // maybe have this stored

const val MY_LOG = "MY_LOG"
