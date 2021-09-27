package com.example.testfirestorev2.testhousematept2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HousemateAPIService {

    private val db = Firebase.firestore
    private var groupIDCollectionDB: CollectionReference = db.collection(HOUSEMATE_COLLECTION)
        .document(GROUP_IDS_DOC).collection(clientGroupIDCollection!!)

    private var _shoppingItems = MutableLiveData<MutableList<ShoppingItem>>()
    //val shoppingItems get
    private var _choreItems = MutableLiveData<MutableList<ChoresItem>>()

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

    // SET UP FUNCTIONS //
    fun setUpRealtimeFetching() {
        Log.d(TAG, "setUpRealtimeFetching: called")
        val shoppingItemCollectionDB = groupIDCollectionDB.document(SHOPPING_LIST_DOC)
            .collection(SHOPPING_ITEMS_COLLECTION)
        val choresItemCollectionDB = groupIDCollectionDB.document(CHORES_LIST_DOC)
            .collection(CHORE_ITEMS_COLLECTION)

        // try .addSnapshotListener from the collection
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
                _shoppingItems.value = allShoppingItems
            }
        }
    }
    // SET UP FUNCTIONS //

    // DATABASE READS //

    // DATABASE WRITES //

}