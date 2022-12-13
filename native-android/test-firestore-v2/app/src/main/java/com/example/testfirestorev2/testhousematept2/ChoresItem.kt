package com.example.testfirestorev2.testhousematept2

data class ChoresItem (
    // Necessary
    val name: String? = "",
    val addedBy: String? = "",
    val completed: Boolean? = false,

    // Not necessary and not used in shoppingItem
    val difficulty: Int? = 1,

    // Not necessary and used in shoppingItem
    val neededBy: String? = "",  //date
    val volunteer: String? = "", //who's buying it
    val priority: Int? = 2
)