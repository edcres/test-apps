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
        .document(GROUP_IDS_DOC)

    companion object {
        const val TAG = "HousematePt2mTAG"
        const val DEFAULT_CLIENT_ID = "00000000asdfg"
        const val HOUSEMATE_COLLECTION = "Test Housemate Pt2"
        const val GROUP_IDS_DOC = "Group IDs"
        const val CLIENT_IDS_DOC = "Client IDs"
        const val SHOPPING_LIST_DOC = "Shopping List"
        const val SHOPPING_ITEMS_COLLECTION = "Shopping Items"
        const val CHORES_LIST_DOC = "Chores List"
        const val CHORE_ITEMS_COLLECTION = "Chore Items"

        // Have the field names in camelcase so it matches the 'ShoppingItem.neededBy' format
        //  this way the fields are correctly fetched from db
        const val LAST_GROUP_ADDED_FIELD = "lastGroupAdded"
        const val LAST_CLIENT_ADDED_FIELD = "lastClientAdded"
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
    fun getShoppingItemsRealtime(clientGroupIDCollection: String): Flow<List<ShoppingItem>> {
        return callbackFlow {
            val listenerRegistration =
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(SHOPPING_LIST_DOC)
                    .collection(SHOPPING_ITEMS_COLLECTION)
                    .addSnapshotListener { querySnapshot: QuerySnapshot?,
                                           firebaseFirestoreException: FirebaseFirestoreException? ->
                        if (firebaseFirestoreException != null) {
                            cancel(
                                message = "Error fetching posts",
                                cause = firebaseFirestoreException
                            )
                            return@addSnapshotListener
                        }

                        if (querySnapshot != null) {
                            val itemsList = querySnapshot.toObjects(ShoppingItem::class.java)
                            offer(itemsList)
                        } else {
                            Log.i(TAG, "getShoppingItemsRealtime: querySnapshot is null")
                        }
                    }
            awaitClose {
                Log.i(TAG, "Cancelling posts listener")
                listenerRegistration.remove()
            }
        }
    }

    fun getChoreItemsRealtime(clientGroupIDCollection: String): Flow<List<ChoresItem>> {
        return callbackFlow {
            val listenerRegistration =
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(CHORES_LIST_DOC)
                    .collection(CHORE_ITEMS_COLLECTION)
                    .addSnapshotListener { querySnapshot: QuerySnapshot?,
                                           firebaseFirestoreException: FirebaseFirestoreException? ->
                        if (firebaseFirestoreException != null) {
                            cancel(
                                message = "Error fetching posts",
                                cause = firebaseFirestoreException
                            )
                            return@addSnapshotListener
                        }

                        if (querySnapshot != null) {
                            val itemsList = querySnapshot.toObjects(ChoresItem::class.java)
                            offer(itemsList)
                        } else {
                            Log.i(TAG, "getChoreItemsRealtime: querySnapshot is null")
                        }
                    }
            awaitClose {
                Log.i(TAG, "Cancelling posts listener")
                listenerRegistration.remove()
            }
        }
    }
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
                Log.i(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.e(TAG, "Error writing document", e) }
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
                Log.i(TAG, "$itemName DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.e(TAG, "Error writing document", e) }
    }

    fun sendVolunteerToDb(clientGroupIDCollection: String, listType: Any, itemName: String, volunteerName: String) {
        when (listType) {
            ShoppingItem::class -> {
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(SHOPPING_LIST_DOC).collection(SHOPPING_ITEMS_COLLECTION)
                    .document(itemName).update(VOLUNTEER_FIELD, volunteerName)
                    .addOnSuccessListener { Log.i(TAG, "$itemName successfully updated to $volunteerName") }
                    .addOnFailureListener { Log.e(TAG, "Error updating doc") }
            }
            ChoresItem::class -> {
                groupIDsDocumentDB.collection(clientGroupIDCollection).document(CHORES_LIST_DOC).collection(CHORE_ITEMS_COLLECTION)
                    .document(itemName).update(VOLUNTEER_FIELD, volunteerName)
                    .addOnSuccessListener { Log.i(TAG, "$itemName successfully updated to $volunteerName") }
                    .addOnFailureListener { Log.e(TAG, "Error updating doc") }
            }
            else -> {
                Log.e(TAG, "sendVolunteerToDb: Error recognizing list Type")
            }
        }
    }
    // DATABASE WRITES //

    // DATABASE READS //
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
            var newIDList = groupIDsDocumentDB.collection(clientGroupIDCollection)
                .get().await().documents.mapNotNull {
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
                }
            // if nothing inside the .mapNotNull{} happens, then there is no previous clientID
            // It only happens once per group creation so it's no big deal to do a second db query
            if (newIDList.isEmpty()) {
                val newID = add1AndScrambleLetters(DEFAULT_CLIENT_ID)
                val firstDocData = hashMapOf<String, Any>(LAST_CLIENT_ADDED_FIELD to newID)

                clientIDsDoc.set(firstDocData)
                    .addOnSuccessListener {
                        Log.i(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error writing document", e)
                    }
                newIDList = listOf(newID)
            }
            newIDList[0]
        } catch (e: Exception) {
            Log.e(TAG, "Error getting client id", e)
            null
        }
    }
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
                Log.e(TAG, "deleteListItem: Error verifying the List Type")
            }
        }

        itemsCollectionDB.document(itemName).delete()
            .addOnSuccessListener { Log.i(TAG, "$itemName document deleted") }
            .addOnFailureListener { Log.e(TAG, "Failure to delete $itemName document") }
    }
    // DELETE DOCUMENT //
}