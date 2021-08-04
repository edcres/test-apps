package com.example.testfirestorev2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// create the database through the client once, but get rid of that code after the database is created
// need a hashmap for groupIDs, clientIDs and shoppingItemContents

// todo:
// -set up database
// -make/get client group id
// -make/get client id

class TestHousemateActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var editTextTest: EditText
    private lateinit var buttonTest: Button
    private lateinit var buttonGetData: Button

    private var clientGroupIDCollection = "abcd1234"
    private var clientIDCollection = "${clientGroupIDCollection}abcd1234"

    companion object {
        private const val TAG = "TestHousemateActyTAG"
        private const val GENERAL_COLLECTION = "generalCollection"
        private const val GROUPS_DOC = "groupIDs"
        private const val CLIENTS_DOC = "clientIDs"
        private const val SHOPPING_LIST = "shoppingList"
        private const val CHORES_LIST = "choresList"

        private const val ID_FIELD = "id"
        private const val NAME_FIELD = "name"
        private const val QUANTITY_FIELD = "quantity"
        private const val ADDED_BY_FIELD = "added_by"
        private const val COMPLETED_FIELD = "completed"
        private const val COST_FIELD = "cost"
        private const val PURCHASE_LOCATION_FIELD = "purchase_location"
        private const val NEEDED_BY_FIELD = "needed_by"
        private const val VOLUNTEER_FIELD = "volunteer"
        private const val PRIORITY_FIELD = "priority"

        private const val DIFFICULTY_FIELD = "difficulty"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_housemate)

        bindWidgetIDs()

        buttonOnClick()
        getButtonOnClick()
    }






    // todo: delete this
    private fun testTheDb() {
        //collection
        val docOne = hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "capital" to false,
            "numberExample" to 3.14159265,
            "timestamp" to FieldValue.serverTimestamp(),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )
        val docTwo = hashMapOf(
            "stringExample2" to "Goodbye world!",
            "booleanExample2" to false,
            "capital2" to true,
            "numberExample2" to 3.14,
            "timestamp2" to FieldValue.serverTimestamp(),
            "listExample2" to arrayListOf(3, 2, 1),
            "nullExample2" to null
        )
        db.collection("data").document("one")
            .set(docOne)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        db.collection("data").document("two")
            .set(docTwo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        //sub-collection
        val docOneOne = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        db.collection("data").document("one")
            .collection("moreData").document("oneOne")
            .set(docOneOne)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
    private fun testTheDbGet() {
        ///////////////// get data (onetime fetch)
        // get documents, I think it comes back in the form of a hashMap
        // get the data from the document
        Log.d(TAG, "testTheDbGet: called")
        val theEditText: EditText = findViewById(R.id.edit_text_test)
        var retrievedDocDataMap: HashMap<String, Any>? = null
        db.collection("data").document("one")
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    retrievedDocDataMap = document.data as HashMap<String, Any>
                    val thePie = retrievedDocDataMap!!["numberExample"]  //todo: 'thePopulation' might always be null
                    theEditText.setText(thePie.toString())
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }




        // todo: in the real app, turn it into an object
        val docRef = db.collection("cities").document("BJ")
        docRef.get().addOnSuccessListener { documentSnapshot ->
//            val city = documentSnapshot.toObject<City>()
        }
    }







    // CLICK LISTENERS //
    private fun buttonOnClick() {
        buttonTest.setOnClickListener {
//            val testString = editTextTest.text//.toString()
//            Log.d(TAG, "$testString")
            testTheDb()     //
        }
    }
    private fun getButtonOnClick() {
        buttonGetData.setOnClickListener{
            testTheDbGet()  //
        }
    }




    // HELPER FUNCTIONS //
    private fun addShoppingItem(
        itemID: Long, itemName: String,
        itemQuantity: Double, addedBy: String,
        completed: Boolean, itemCost: Double,
        purchaseLocation: String, neededBy: String,   // try and make this a date
        volunteer: String, itemPriority: Int
    ) {

        val shoppingItemData = hashMapOf(
            ID_FIELD to itemID,     // idk if I need this, make it a long
            NAME_FIELD to itemName,
            QUANTITY_FIELD to itemQuantity,
            ADDED_BY_FIELD to addedBy,
            COMPLETED_FIELD to completed,
            COST_FIELD to itemCost,
            PURCHASE_LOCATION_FIELD to purchaseLocation,
            NEEDED_BY_FIELD to neededBy,
            VOLUNTEER_FIELD to volunteer,
            PRIORITY_FIELD to itemPriority
        )

        // access the clientGroup, then the client, then the shopping item
        db.collection(GENERAL_COLLECTION).document(GROUPS_DOC)
            .collection(clientGroupIDCollection).document(CLIENTS_DOC)
            .collection(clientIDCollection).document(SHOPPING_LIST)
            .set(shoppingItemData)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    private fun addChoreItem(
        itemID: Long, itemName: String,
        addedBy: String, completed: Boolean,
        difficulty: Int, neededBy: String,   // try and make this a date
        volunteer: String, itemPriority: Int
    ) {
        val choresItemData = hashMapOf(
            ID_FIELD to itemID,     // idk if I need this, make it a long
            NAME_FIELD to itemName,
            ADDED_BY_FIELD to addedBy,
            COMPLETED_FIELD to completed,
            DIFFICULTY_FIELD to difficulty,
            NEEDED_BY_FIELD to neededBy,
            VOLUNTEER_FIELD to volunteer,
            PRIORITY_FIELD to itemPriority
        )

        // access the clientGroup, then the client, then the shopping item
        db.collection(GENERAL_COLLECTION).document(GROUPS_DOC)
            .collection(clientGroupIDCollection).document(CLIENTS_DOC)
            .collection(clientIDCollection).document(CHORES_LIST)
            .set(choresItemData)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        editTextTest = findViewById(R.id.edit_text_test)
        buttonTest = findViewById(R.id.button_test)
        buttonGetData = findViewById(R.id.button_test_2)
    }
}
