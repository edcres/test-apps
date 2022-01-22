package com.example.testfirestorev2.testhousematept2

import android.util.Log
import com.example.testfirestorev2.add1AndScrambleLetters
import com.google.firebase.firestore.*
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
    private var groupIDsDocumentDB: DocumentReference = db.collection(HOUSEMATE_COLLECTION)
        .document(GROUP_IDS_DOC)//.collection(clientGroupIDCollection)

    companion object {
        const val TAG = "HousematePt2mTAG"

        const val HOUSEMATE_COLLECTION = "Test Housemate Pt2"
        const val GROUP_IDS_DOC = "Group IDs"
        const val CLIENT_IDS_DOC = "Client IDs"
        const val SHOPPING_LIST_DOC = "Shopping List"
        const val SHOPPING_ITEMS_COLLECTION = "Shopping Items"
        const val CHORES_LIST_DOC = "Chores List"
        const val CHORE_ITEMS_COLLECTION = "Chore Items"
        const val LAST_GROUP_ADDED_FIELD = "lastGroupAdded"
        const val LAST_CLIENT_ADDED_FIELD = "lastClientAdded"

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
    // for addSnapshotListener, use flow instead of coroutines or livedata. Use coroutines
    // when calling the 'getPosts()' function
    /* Steps Here:
        - Creating a listener registration inside a callback flow
        - Cancelling the registration in case of any error
        - Emitting the results via the this.trySend() method
        - Calling awaitClose
     */
    // don't need suspend functions with Flow
//    fun getShoppingItemsRealtime(clientGroupIDCollection: String): Flow<MutableList<ShoppingItem>> {
//        return callbackFlow {
//            val listenerRegistration =
//                groupIDsDocumentDB.collection(clientGroupIDCollection).document(SHOPPING_LIST_DOC)
//                    .collection(SHOPPING_ITEMS_COLLECTION)
//                    .addSnapshotListener { querySnapshot: QuerySnapshot?,
//                                           firebaseFirestoreException: FirebaseFirestoreException? ->
//                        if (firebaseFirestoreException != null) {
//                            cancel(
//                                message = "Error fetching posts",
//                                cause = firebaseFirestoreException
//                            )
//                            return@addSnapshotListener
//                        }
//
//                        if (querySnapshot != null) {
//                            val itemsList = querySnapshot.toObjects(ShoppingItem::class.java)
//
//                            this.trySend(itemsList)
//                        } else {
//                            Log.d(TAG, "getPosts: querySnapshot is null")
//                        }
//                    }
//            awaitClose {
//                Log.d(TAG, "Cancelling posts listener")
//                listenerRegistration.remove()
//            }
//        }
//    }

//    fun getChoreItemsRealtime(clientGroupIDCollection: String): Flow<MutableList<ChoresItem>> {
//        return callbackFlow {
//            val listenerRegistration = groupIDsDocumentDB.collection(clientGroupIDCollection).document(CHORES_LIST_DOC)
//                .collection(CHORE_ITEMS_COLLECTION).addSnapshotListener {
//                        querySnapshot: QuerySnapshot?,
//                        firebaseFirestoreException: FirebaseFirestoreException? ->
//                    if (firebaseFirestoreException != null) {
//                        cancel(message = "Error fetching posts",
//                            cause = firebaseFirestoreException)
//                        return@addSnapshotListener
//                    }
//
//                    if(querySnapshot != null) {
//                        val itemsList = querySnapshot.toObjects(ChoresItem::class.java)
//                        this.trySend(itemsList)
//                    } else {
//                        Log.d(TAG, "getPosts: querySnapshot is null")
//                    }
//                }
//            awaitClose {
//                Log.d(TAG, "Cancelling posts listener")
//                listenerRegistration.remove()
//            }
//        }
//    }
    // SET UP FUNCTIONS //

    // DATABASE WRITES //
    fun addShoppingItemToDatabase(
        clientGroupIDCollection: String,
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
        groupIDsDocumentDB.collection(clientGroupIDCollection).document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
            .document(itemName).set(shoppingItemData)
            .addOnSuccessListener {
                Log.d(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.d(TAG, "Error writing document", e) }

    }
    fun addChoresItemToDatabase(
        clientGroupIDCollection: String,
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
        groupIDsDocumentDB.collection(clientGroupIDCollection).document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
            .document(itemName).set(choresItemData)
            .addOnSuccessListener {
                Log.d(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.d(TAG, "Error writing document", e) }
    }

    fun sendVolunteerToDb(clientGroupIDCollection: String, listType: Any, itemName: String, volunteerName: String) {

        when (listType) {
            ShoppingItem::class -> {
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
                    .document(itemName).update(VOLUNTEER_FIELD, volunteerName)
                    .addOnSuccessListener { Log.d(TAG, "$itemName successfully updated to $volunteerName") }
                    .addOnFailureListener { Log.d(TAG, "Error updating doc") }
            }
            ChoresItem::class -> {
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
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
    // todo: delete: I'm pretty sure I don't need this function
    //  bc getting it realtime makes this obsolete
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

    // get the last group added String (and update it to the new ID)
    suspend fun getLastGroupAdded(): String? {
        return try {
            // This is an inefficient query (also in 'getLastClientAdded()')
                // I'm getting 3 documents with all their info, but I only need one
            // todo: My goal was to go directly to the CLIENT_IDS_DOC doc and get the id.
            //  all while writing to the db in the same remote query
            // Update, it looks like 'it.data' points directly to the GroupIDs document. Idk why
            val newIDList = db.collection(HOUSEMATE_COLLECTION).get().await().documents.mapNotNull {
                val oldID = it.data!![LAST_GROUP_ADDED_FIELD] as String
                val newID = add1AndScrambleLetters(oldID)

                Log.i(TAG, "getLastGroupAdded: new group id created")
                db.collection(HOUSEMATE_COLLECTION).document(GROUP_IDS_DOC)
                    .update(LAST_GROUP_ADDED_FIELD, newID)
                    .addOnSuccessListener {
                        Log.i(TAG, "getLastGroupAdded: LAST_GROUP_ADDED_FIELD updated")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "getLastGroupAdded: database fetch failed", e)
                    }
                newID
            }
            newIDList[0]
        } catch (e: Exception) {
            Log.e(TAG, "Error getting group id", e)
            null
        }
    }

    suspend fun getLastClientAdded(clientGroupIDCollection: String): String? {
        val clientIDsDoc =
            groupIDsDocumentDB.collection(clientGroupIDCollection).document(CLIENT_IDS_DOC)
        return try {
            Log.d(TAG, "getLastClientAdded: this happens 1")
            val newIDList = groupIDsDocumentDB.collection(clientGroupIDCollection)
                .get().await().documents.mapNotNull {
                    Log.d(TAG, "getLastClientAdded: this happens 2")
                    if (it.data != null) {
                        val oldID = it.data!![LAST_CLIENT_ADDED_FIELD] as String
                        val newID = add1AndScrambleLetters(oldID)
                        Log.i(TAG, "getLastClientAdded: new client id created")

                        clientIDsDoc.update(LAST_CLIENT_ADDED_FIELD, newID)
                            .addOnSuccessListener {
                                Log.i(TAG, "getLastClientAdded: LAST_CLIENT_ADDED_FIELD updated")
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "getLastClientAdded: database fetch failed", e)
                            }
                        newID
                    } else {
                        Log.d(TAG, "getLastClientAdded: it.data is null")
                        val newID = add1AndScrambleLetters("00000000asdfg")
                        val firstDocData = hashMapOf<String, Any>(LAST_CLIENT_ADDED_FIELD to newID)

                        clientIDsDoc.set(firstDocData)
                            .addOnSuccessListener {
                                Log.i(TAG, "DocumentSnapshot successfully written!")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error writing document", e)
                            }
                        newID
                    }
                }
            Log.d(TAG, "getLastClientAdded: $newIDList")
            newIDList[0]
        } catch (e: Exception) {
            Log.e(TAG, "Error getting client id", e)
            null
        }
    }

    // todo: delete this
//    suspend fun oldGetLastClientAdded(clientGroupIDCollection: String): String {
//        val lastClientAddedField = "last client added"
//        var oldID: String
//        var newID: String?
//        val clientsDocDb =
//            groupIDsDocumentDB.collection(clientGroupIDCollection).document(CLIENT_IDS_DOC)
//        clientsDocDb.get()
//            .addOnSuccessListener { document ->
//                if (document.data != null) {
//                    val clientIdDoc = document.data as Map<String, Any>
//                    oldID = clientIdDoc[lastClientAddedField] as String
//                    newID = add1AndScrambleLetters(oldID)
//                    // Update new id to the database as the last id added
//                    clientsDocDb.update(lastClientAddedField, newID)
//                        .addOnSuccessListener {
//
//                            // todo: set the value of newID from the viewModel
//                            //  set the SP from the viewModel
//                            housemate2ViewModel.clientIDCollection = newID
//                            housemate2ViewModel
//                                .sendDataToSP(housemate2ViewModel.clientIdSPTag, newID!!)
//                        }
//                        .addOnFailureListener { e ->
//                            Log.d("DB Query", "Error updating client doc", e)
//                        }
//                } else {
//                    // the document will be null for the first member in the group
//                    //  -there is no client id to get, make a new clientID and add it to the db
//                    newID = add1AndScrambleLetters("00000000asdfg")
//                    val firstDocData = hashMapOf<String, Any>(lastClientAddedField to newID!!)
//                    clientsDocDb.set(firstDocData)
//                        .addOnSuccessListener {
//                            Log.d(TAG, "DocumentSnapshot successfully written!")
//                            housemate2ViewModel.clientIDCollection = newID
//                            housemate2ViewModel
//                                .sendDataToSP(housemate2ViewModel.clientIdSPTag, newID!!)
//                        }
//                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
//                }
//            }
//    }
    // DATABASE READS //

    // DELETE DOCUMENT //
    fun deleteListItem(clientGroupIDCollection: String, listType: Any, itemName: String) {
        var itemsCollectionDB: CollectionReference =
            groupIDsDocumentDB.collection(clientGroupIDCollection)
        when (listType) {
            ShoppingItem::class -> {
                itemsCollectionDB = groupIDsDocumentDB.collection(clientGroupIDCollection)
                        .document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
            }
            ChoresItem::class -> {
                itemsCollectionDB = groupIDsDocumentDB.collection(clientGroupIDCollection)
                    .document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
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