package com.example.testfirestorev2.testhousematept2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testfirestorev2.add1AndScrambleLetters
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

// https://medium.com/firebase-developers/android-mvvm-firestore-37c3a8d65404

// for addSnapshotListener (real-time changes), use flow instead of coroutines or livedata.
//      Use coroutines when calling the 'getPosts()' (one time call) function
// https://stackoverflow.com/questions/63889054/use-kotlin-coroutines-for-real-time-firestore-updates

// for .get and .set fireStore queries, use suspend functions and coroutines

class HousemateAPIService {

    private val db = Firebase.firestore
    private var groupIDCollectionDB: CollectionReference = db.collection(HOUSEMATE_COLLECTION)
        .document(GROUP_IDS_DOC).collection(housemate2ViewModel.clientGroupIDCollection!!)

    companion object {
        const val TAG = "ApiServiceTAG"

        const val HOUSEMATE_COLLECTION = "Test Housemate Pt2"
        const val GROUP_IDS_DOC = "Group IDs"
        const val CLIENT_IDS_DOC = "Client IDs"
        const val SHOPPING_LIST_DOC = "Shopping List"
        const val SHOPPING_ITEMS_COLLECTION = "Shopping Items"
        const val CHORES_LIST_DOC = "Chores List"
        const val CHORE_ITEMS_COLLECTION = "Chore Items"

        // Have the field names in camelcase so it matches the 'ShoppingItem.neededBy' format
        //  this way the fields are correctly fetched from db
        const val NAME_FIELD = "name"
        const val QUANTITY_FIELD = "quantity"
        const val ADDED_BY_FIELD = "addedBy"
        const val COMPLETED_FIELD = "completed"
        const val COST_FIELD = "cost"
        const val PURCHASE_LOCATION_FIELD = "purchaseLocation"
        const val NEEDED_BY_FIELD = "neededBy"
        const val VOLUNTEER_FIELD = "volunteer"
        const val PRIORITY_FIELD = "priority"
        const val DIFFICULTY_FIELD = "difficulty"
    }

    // SET UP FUNCTIONS //
    // for addSnapshotListener, use flow instead of coroutines or livedata. Use coroutines when calling the 'getPosts()' function
    /* Steps Here:
        - Creating a listener registration inside a callback flow
        - Cancelling the registration in case of any error
        - Emitting the results via the this.trySend() method
        - Calling awaitClose
     */
    // don't need suspend functions with Flow
    fun getShoppingItemsRealtime(): Flow<MutableList<ShoppingItem>> {
        return callbackFlow {
            val listenerRegistration = groupIDCollectionDB.document(SHOPPING_LIST_DOC)
                .collection(SHOPPING_ITEMS_COLLECTION).addSnapshotListener {
                        querySnapshot: QuerySnapshot?,
                        firebaseFirestoreException: FirebaseFirestoreException? ->
                    if (firebaseFirestoreException != null) {
                        cancel(message = "Error fetching posts",
                            cause = firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    if(querySnapshot != null) {
                        val itemsList = querySnapshot.toObjects(ShoppingItem::class.java)
                        this.trySend(itemsList)
                    } else {
                        Log.d(TAG, "getPosts: querySnapshot is null")
                    }
                }
            awaitClose {
                Log.d(TAG, "Cancelling posts listener")
                listenerRegistration.remove()
            }
        }
    }

    fun getChoreItemsRealtime(): Flow<MutableList<ChoresItem>> {
        return callbackFlow {
            val listenerRegistration = groupIDCollectionDB.document(CHORES_LIST_DOC)
                .collection(CHORE_ITEMS_COLLECTION).addSnapshotListener {
                        querySnapshot: QuerySnapshot?,
                        firebaseFirestoreException: FirebaseFirestoreException? ->
                    if (firebaseFirestoreException != null) {
                        cancel(message = "Error fetching posts",
                            cause = firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    if(querySnapshot != null) {
                        val itemsList = querySnapshot.toObjects(ChoresItem::class.java)
                        this.trySend(itemsList)
                    } else {
                        Log.d(TAG, "getPosts: querySnapshot is null")
                    }
                }
            awaitClose {
                Log.d(TAG, "Cancelling posts listener")
                listenerRegistration.remove()
            }
        }
    }
    // SET UP FUNCTIONS //

    // DATABASE WRITES //
    fun addShoppingItemToDatabase(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        val shoppingItemData = hashMapOf(
            NAME_FIELD to itemName,
            QUANTITY_FIELD to itemQuantity,
            COST_FIELD to itemCost,
            PURCHASE_LOCATION_FIELD to purchaseLocation,
            NEEDED_BY_FIELD to itemNeededBy,
            PRIORITY_FIELD to itemPriority,
            COMPLETED_FIELD to false,
            VOLUNTEER_FIELD to "",
            ADDED_BY_FIELD to addedBy
        )
        groupIDCollectionDB.document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
            .document(itemName).set(shoppingItemData)
            .addOnSuccessListener {
                Log.d(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.d(TAG, "Error writing document", e) }

    }
    fun addChoresItemToDatabase(
        itemName: String,
        itemDifficulty: Int,
        itemNeededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        val choresItemData = hashMapOf(
            NAME_FIELD to itemName,
            DIFFICULTY_FIELD to itemDifficulty,
            NEEDED_BY_FIELD to itemNeededBy,
            PRIORITY_FIELD to itemPriority,
            ADDED_BY_FIELD to addedBy
        )
        groupIDCollectionDB.document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
            .document(itemName).set(choresItemData)
            .addOnSuccessListener {
                Log.d(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.d(TAG, "Error writing document", e) }
    }

    fun sendVolunteerToDb(listType: Any, itemName: String, volunteerName: String) {

        when (listType) {
            ShoppingItem::class -> {
                groupIDCollectionDB.document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
                    .document(itemName).update(VOLUNTEER_FIELD, volunteerName)
                    .addOnSuccessListener { Log.d(TAG, "$itemName successfully updated to $volunteerName") }
                    .addOnFailureListener { Log.d(TAG, "Error updating doc") }
            }
            ChoresItem::class -> {
                groupIDCollectionDB.document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
                    .document(itemName).update(VOLUNTEER_FIELD, volunteerName)
                    .addOnSuccessListener { Log.d(TAG, "$itemName successfully updated to $volunteerName") }
                    .addOnFailureListener { Log.d(TAG, "Error updating doc") }
            }
            else -> {
                Log.d(TAG, "sendVolunteerToDb: Error recognizing list Type")
            }
        }

    }
    // DATABASE WRITES //

    // DATABASE READS //
    //from medium.com       .get()
    object FirebaseProfileService {
        suspend fun getShoppingData(userId: String): ShoppingItem? {
            val db = Firebase.firestore
            return try {
                db.collection("users")
                    .document(userId).get().await().toObject(ShoppingItem::class.java)
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

    // get the last group added String (and update it to the new ID)
    fun getLastGroupAdded() {
        // remember to start off the database with last group added '00000000asdfg'
        // ie. 00000001asdfg, 00000002fagsd, 00000003sgdfa ...
        val lastGroupAddedField = "last group added"
        var oldID: String
        var newID: String

        // get old ID
         db.collection(HOUSEMATE_COLLECTION).document(GROUP_IDS_DOC)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val groupIdDoc = document.data as Map<String, Any>
                    oldID = groupIdDoc[lastGroupAddedField] as String
                    newID = add1AndScrambleLetters(oldID)


                    housemate2ViewModel.clientGroupIDCollection = newID


                    Log.d(TAG, "getLastGroupAdded: new group $newID")
                    // update last ID added
                    db.collection(HOUSEMATE_COLLECTION).document(GROUP_IDS_DOC)
                        .update(lastGroupAddedField, newID)
                        .addOnSuccessListener {
                            Log.d(TAG, "generateClientGroupID: lastGroupAddedField updated")
                            housemate2ViewModel.sendDataToSP(
                                housemate2ViewModel.groupIdSPTag,
                                newID
                            )
                            housemate2ViewModel.setClientID()
                        }
                } else {
                    Log.d(TAG, "generateClientGroupID: groupIDs document is null")
                }
            }
             .addOnFailureListener { e ->
                Log.d(TAG, "generateClientGroupID: database fetch failed", e)
            }
    }

    fun getLastClientAdded() {
        val lastClientAddedField = "last client added"
        var oldID: String
        var newID: String?
        val clientsDocDb = groupIDCollectionDB.document(CLIENT_IDS_DOC)
        clientsDocDb.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    val clientIdDoc = document.data as Map<String, Any>
                    oldID = clientIdDoc[lastClientAddedField] as String
                    newID = add1AndScrambleLetters(oldID)
                    // Update new id to the database as the last id added
                    clientsDocDb.update(lastClientAddedField, newID)
                        .addOnSuccessListener {
                            housemate2ViewModel.clientIDCollection = newID
                            housemate2ViewModel
                                .sendDataToSP(housemate2ViewModel.clientIdSPTag, newID!!)
                        }
                        .addOnFailureListener { e ->
                            Log.d("DB Query", "Error updating client doc", e)
                        }
                } else {
                    // the document will be null for the first member in the group
                    //  -there is no client id to get, make a new clientID and add it to the db
                    newID = add1AndScrambleLetters("00000000asdfg")
                    val firstDocData = hashMapOf<String, Any>(lastClientAddedField to newID!!)
                    clientsDocDb.set(firstDocData)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully written!")
                            housemate2ViewModel.clientIDCollection = newID
                            housemate2ViewModel
                                .sendDataToSP(housemate2ViewModel.clientIdSPTag, newID!!)
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                }
            }
    }
    // DATABASE READS //

    // DELETE DOCUMENT //
    fun deleteListItem(listType: Any, itemName: String) {
        var itemsCollectionDB: CollectionReference = groupIDCollectionDB
        when (listType) {
            ShoppingItem::class -> {
                itemsCollectionDB = groupIDCollectionDB.document(SHOPPING_LIST_DOC)
                    .collection(SHOPPING_ITEMS_COLLECTION)
            }
            ChoresItem::class -> {
                itemsCollectionDB = groupIDCollectionDB.document(CHORES_LIST_DOC)
                    .collection(CHORE_ITEMS_COLLECTION)
            }
            else -> {
                Log.d(TAG, "deleteListItem: Error verifying the List Type")
            }
        }

        itemsCollectionDB.document(itemName).delete()
            .addOnSuccessListener { Log.d(TAG, "$itemName document deleted") }
            .addOnFailureListener { Log.d(TAG, "Failure to delete $itemName document") }
    }
    // DELETE DOCUMENT //
}