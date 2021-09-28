package com.example.testfirestorev2.testhousematept2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testfirestorev2.testhousematept2.ShoppingItem.Companion.toShoppingItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

// https://medium.com/firebase-developers/android-mvvm-firestore-37c3a8d65404

class HousemateAPIService {

    private val db = Firebase.firestore
    private var groupIDCollectionDB: CollectionReference = db.collection(HOUSEMATE_COLLECTION)
        .document(GROUP_IDS_DOC).collection(clientGroupIDCollection!!)

    private val shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    private val choreItems = MutableLiveData<MutableList<ChoresItem>>()

    companion object {
        const val TAG = "ApiServiceTAG"

        const val HOUSEMATE_COLLECTION = "Test Housemate Pt2"
        const val GROUP_IDS_DOC = "Group IDs"
        const val CLIENT_IDS_DOC = "Client IDs"
        const val SHOPPING_LIST_DOC = "Shopping List"
        const val SHOPPING_ITEMS_COLLECTION = "Shopping Items"
        const val CHORES_LIST_DOC = "Chores List"
        const val CHORE_ITEMS_COLLECTION = "Chore Items"

        // these might be needed in the viewModel and passed to here
        var clientGroupIDCollection: String? = null
        var clientIDCollection: String? = null

        const val NAME_FIELD = "name"
        const val QUANTITY_FIELD = "quantity"
        const val ADDED_BY_FIELD = "added by"
        const val COMPLETED_FIELD = "completed"
        const val COST_FIELD = "cost"
        const val PURCHASE_LOCATION_FIELD = "purchase location"
        const val NEEDED_BY_FIELD = "needed by"
        const val VOLUNTEER_FIELD = "volunteer"
        const val PRIORITY_FIELD = "priority"
        const val DIFFICULTY_FIELD = "difficulty"
    }









    //from medium.com
    object FirebaseProfileService {
        suspend fun getShoppingData(userId: String): ShoppingItem? {
            val db = Firebase.firestore
            return try {
                db.collection("users")
                    .document(userId).get().await().toShoppingItem()
//                    .document(userId).get().await().toShoppingItem()
            } catch (e: Exception) {
                Log.e(TAG, "Error getting user details", e)
//                FirebaseCrashlytics.getInstance().log("Error getting user details")
//                FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
//                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
    }


    // my implementation copying medium.com
    suspend fun setUpShoppingRealtimeFetching(): MutableList<ShoppingItem> {
        return try {
            val shoppingItemCollectionDB = groupIDCollectionDB.document(SHOPPING_LIST_DOC)
                .collection(SHOPPING_ITEMS_COLLECTION)
            shoppingItemCollectionDB.addSnapshotListener { snapshot, e ->

            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
//                FirebaseCrashlytics.getInstance().log("Error getting user details")
//                FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
//                FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }






    // SET UP FUNCTIONS //
    fun setUpRealtimeFetching() {
        Log.d(TAG, "setUpRealtimeFetching: called")
        val shoppingItemCollectionDB = groupIDCollectionDB.document(SHOPPING_LIST_DOC)
            .collection(SHOPPING_ITEMS_COLLECTION)
        val choresItemCollectionDB = groupIDCollectionDB.document(CHORES_LIST_DOC)
            .collection(CHORE_ITEMS_COLLECTION)


        // fill the '_shoppingItems' list
        shoppingItemCollectionDB.addSnapshotListener { snapshot, e ->

            if (e != null) {
                // if there is an exception, skip the listener
                Log.d(TAG, "setUpRealtimeFetching: DB Query Fail in Shopping.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // turn each shopping item into an object
                val allShoppingItems =
                    snapshot.toObjects(ShoppingItem::class.java) as MutableList<ShoppingItem>
                shoppingItems.value = allShoppingItems
            }
        }
        // fill the '_choreItems' list
        choresItemCollectionDB.addSnapshotListener { snapshot, e ->

            if (e != null) {
                // if there is an exception, skip the listener
                Log.d(TAG, "setUpRealtimeFetching: DB Query Fail in Shopping.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // turn each shopping item into an object
                val allChoreItems =
                    snapshot.toObjects(ChoresItem::class.java) as MutableList<ChoresItem>
                choreItems.value = allChoreItems
            }
        }
    }
    // SET UP FUNCTIONS //

    // DATABASE READS //

    // DATABASE WRITES //

}