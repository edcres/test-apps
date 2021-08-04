package com.example.testfirestorev2

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FromDocumentation {

    private val db = Firebase.firestore

    private fun addingDifferentDataTypes() {
        //You can set a field in your document to a server timestamp which tracks when the server receives the update.
        val docData = hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "capital" to false,
            "numberExample" to 3.14159265,
            "timestamp" to FieldValue.serverTimestamp(),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )

        val nestedData = hashMapOf(
            "a" to 5,
            "b" to true
        )

        // add the other hashMap inside the docData hashMap
        docData["objectExample"] = nestedData

        db.collection("data").document("one")
            .set(docData)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
        // use 'add()' instead of 'set()' on a document to autogenerate a document id

        // update nested objects, use "dot notation" to reference nested fields within the document when you call update()
        val dataExampleDB = db.collection("data").document("one")
            .update(mapOf(
                "objectExample.b" to false
            ))
    }

    private fun updateDoc() {
        // To update some fields of a document without overwriting the entire document, use the update() method:
        val washingtonRef = db.collection("cities").document("DC")

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("capital", true)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
    }

    private fun incrementValue() {
        val washingtonRef = db.collection("cities").document("DC")

        // Atomically increment the population of the city by 50.
        washingtonRef.update("population", FieldValue.increment(50))
    }

    private fun deleteADocument() {
        // To delete a document, use the delete() method
        // Deleting a document does not delete its sub-collections!
        //   -You can still access the sub-collection documents by reference: '/mycoll/mydoc/mysubcoll/mysubdoc'
        db.collection("cities").document("DC")
            .delete()
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
    }

    private fun deleteField() {
        // To delete specific fields from a document, use the FieldValue.delete() method when you update a document:
        val docRef = db.collection("cities").document("BJ")

        // Remove the 'capital' field from the document
        val updates = hashMapOf<String, Any>(
            "capital" to FieldValue.delete()
        )

        docRef.update(updates).addOnCompleteListener { }
    }

    private fun getOneTimeData() {
        // Create a new user with a first and last name
        val user1 = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        //retrieve data
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getDBData() {
        //set data
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        // get data from cache (could also be straight from the remote db)
        val docRefSF = db.collection("cities").document("SF")
        val source = Source.CACHE   // get from the offline cache
        docRefSF.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    Log.d(ContentValues.TAG, "Cached document data: ${document.data}")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            } else {
                Log.d(ContentValues.TAG, "get failed: ", task.exception)
            }
        }

        // turn the document data to an object
        val docRefBJ = db.collection("cities").document("BJ")
        docRefBJ.get().addOnSuccessListener { documentSnapshot ->
//            val city = documentSnapshot.toObject<City>()      //idk what they mean by 'toObject'
        }

        // use where() to query for all of the documents that meet a certain condition
        // you can retrieve all documents in a collection by omitting the 'where()' filter entirely
        db.collection("cities")
            .whereEqualTo("capital", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun getDataRealtime() {
        val docRef = db.collection("cities").document("SF")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }

    private fun listenToDocumentsRealtime() {
        // with documents, you can use onSnapshot() instead of get() to listen to the results of a query
        db.collection("cities")
            .whereEqualTo("state", "CA")    // listen to the docs
            //.whereArrayContains("regions", "west_coast")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val cities = ArrayList<String>()
                for (doc in value!!) {
                    doc.getString("name")?.let {
                        cities.add(it)
                    }
                }
                Log.d(ContentValues.TAG, "Current cites in CA: $cities")
            }
    }

    private fun paginateWithQueryCursors() {
        //'.limit()'
        db.collection("cities")
            .orderBy("population")
            .startAt(1000000)   // or 'startAfter()'
        db.collection("cities")
            .orderBy("population")
            .endAt(5000000)     // or 'endBefore()'

        //Paginate queries by combining query cursors with the '.limit()' method
        // Construct query for first 25 cities, ordered by population, then get the next 25 cities
        val first = db.collection("cities")
            .orderBy("population")
            .limit(25)
        first.get()
            .addOnSuccessListener { documentSnapshots ->
                // ...

                // Get the last visible document
                val lastVisible = documentSnapshots.documents[documentSnapshots.size() - 1]

                // Construct a new query starting at this document,
                // get the next 25 cities.
                val next = db.collection("cities")
                    .orderBy("population")
                    .startAfter(lastVisible)
                    .limit(25)

                // Use the query for pagination
                // ...
            }
    }

    // Check whether you're receiving data from the server or the cache 'querySnapshot.metadata.isFromCache'
    private fun dataFromPersistenceCache() {
        // If fromCache is true, the data came from the cache and might be stale or incomplete.
        // import: MetadataChanges and DocumentChange
        db.collection("cities").whereEqualTo("state", "CA")
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen error", e)
                    return@addSnapshotListener
                }
                for (change in querySnapshot!!.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d(ContentValues.TAG, "New city: ${change.document.data}")
                    }

                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                    Log.d(ContentValues.TAG, "Data fetched from $source")
                }
            }
    }
}

// By default, use realtime queries over manual fetches

/*
structure data in Firestore:
    Documents
    Multiple collections
    Sub-collections within documents
 */

// Can delete collections, documents, and fields.
// Deleting collections is not recommended for android
//      - to delete an entire collection or subcollection in Cloud Firestore, retrieve all the documents within the collection or subcollection and delete them

// Realtime Data Updates
//  Send data changes directly yto the remote db, not to the local cache, the cache will be updated from the remote db

/* Atomic operations for reading and writing data: (avoids other user writing over your data)

    Batched Writes: a batched write is a set of write operations on one or more documents.
    (performs queries atomically, either everything happens or nothing happens)

    Transactions: a transaction is a set of read and write operations on one or more documents.
    (reads documents first b4 performing query, makes sure db isn't updated by another user until the transaction is complete)
 */

/* two ways to retrieve data stored in Cloud Firestore:
    Call a method to get the data.
    Set a listener to receive data-change events
 */

// Detach a listener:
// When you are no longer interested in listening to your data, you must detach your listener so that your event callbacks stop getting called
//      val registration = query.addSnapshotListener { snapshots, e ->
//      'registration.remove()'

/*
rules: (transaction refers to both transaction and batched writes)
1- perform reads before writes
2- no side effects (ie. don't increment variables in the middle of you transaction)
3- don't go overboard with the number of docs that are in a single transaction (only what is logically related)
4- transaction will fail if offline
5- can only write up to 500 documents at a time in a transaction
 */

// For Android and iOS, offline persistence is enabled by default
