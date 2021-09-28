package com.example.testfirestorev2.testhousematept2

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot

data class ShoppingItem (
    // Necessary
    val id: Long? = 0,
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
) {
    // this replaces the 'toObject()' method provided by Firestore
    companion object {
        fun DocumentSnapshot.toShoppingItem(): ShoppingItem? {
            try {
                //todo: these might not be correct
                //todo: either way, try to replace it with the toObject() method
                val id = getLong("id")
                val name = getString("name")
                val quantity = getDouble("quantity")
                val addedBy = getString("added by")
                val purchased = getBoolean("purchased")
                val cost = getDouble("cost")
                val purchaseLocation = getString("purchase location")
                val neededBy = getString("needed by")
                val volunteer = getString("volunteer")
                val priority = getDouble("priority")!!.toInt()
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
//                FirebaseCrashlytics.getInstance().log("Error converting user profile")
//                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
//                FirebaseCrashlytics.getInstance().recordException(e)
                return null
            }
        }
        private const val TAG = "User"
        }
    }
}