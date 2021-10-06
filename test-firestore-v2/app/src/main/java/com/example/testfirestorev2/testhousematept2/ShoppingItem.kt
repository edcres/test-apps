package com.example.testfirestorev2.testhousematept2

data class ShoppingItem(
    // Necessary
    val name: String? = "",
    val quantity: Double? = 0.0,
    val addedBy: String? = "",
    val purchased: Boolean? = false,
    // Not necessary and not used in Chores
    val cost: Double? = 0.0,
    val purchaseLocation: String? = "",

    // Not necessary and used in chores
    val neededBy: String? = "",  //date
    val volunteer: String? = "", //who's buying it
    val priority: Int? = 2
)