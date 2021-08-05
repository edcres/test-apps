package com.example.testfirestorev2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// Create the database through the client once, but get rid of that code after the database is created.
// Need a hashmap for groupIDs, clientIDs and shoppingItemContents.

// todo:
// -set up database
// -make/get client group id
// -make/get client id

class TestHousemateActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var editTextTest: EditText

    private var clientGroupIDCollection = "abcd1234"
    private var clientIDCollection = "${clientGroupIDCollection}abcd1234"

    private lateinit var i1shoppingItIsDone: CheckBox
    private lateinit var i1shoppingItemQty: TextView
    private lateinit var i1shoppingItemName: TextView
    private lateinit var i1shoppingWhenNeededDoneText: TextView
    private lateinit var i1shoppingWhereText: TextView
    private lateinit var i1shoppingCostText: TextView
    private lateinit var i1shoppingWhoIsGettingItText: TextInputEditText
    private lateinit var i1shoppingPriorityText: TextView
    private lateinit var i1shoppingAddedByText: TextView
    private lateinit var i2shoppingItIsDone: CheckBox
    private lateinit var i2shoppingItemQty: TextView
    private lateinit var i2shoppingItemName: TextView
    private lateinit var i2shoppingWhenNeededDoneText: TextView
    private lateinit var i2shoppingWhereText: TextView
    private lateinit var i2shoppingCostText: TextView
    private lateinit var i2shoppingWhoIsGettingItText: TextInputEditText
    private lateinit var i2shoppingPriorityText: TextView
    private lateinit var i2shoppingAddedByText: TextView
    private lateinit var i3shoppingItIsDone: CheckBox
    private lateinit var i3shoppingItemQty: TextView
    private lateinit var i3shoppingItemName: TextView
    private lateinit var i3shoppingWhenNeededDoneText: TextView
    private lateinit var i3shoppingWhereText: TextView
    private lateinit var i3shoppingCostText: TextView
    private lateinit var i3shoppingWhoIsGettingItText: TextInputEditText
    private lateinit var i3shoppingPriorityText: TextView
    private lateinit var i3shoppingAddedByText: TextView
    private lateinit var i1choresItIsDone: CheckBox
    private lateinit var i1choresItemName: TextView
    private lateinit var i1choresWhenNeededDoneText: TextView
    private lateinit var i1choresDifficulty: TextView
    private lateinit var i1choresWhoIsDoingItText: TextInputEditText
    private lateinit var i1choresPriorityText: TextView
    private lateinit var i1choresAddedByText: TextView
    private lateinit var i2choresItIsDone: CheckBox
    private lateinit var i2choresItemName: TextView
    private lateinit var i2choresWhenNeededDoneText: TextView
    private lateinit var i2choresDifficulty: TextView
    private lateinit var i2choresWhoIsDoingItText: TextInputEditText
    private lateinit var i2choresPriorityText: TextView
    private lateinit var i2choresAddedByText: TextView
    private lateinit var i3choresItIsDone: CheckBox
    private lateinit var i3choresItemName: TextView
    private lateinit var i3choresWhenNeededDoneText: TextView
    private lateinit var i3choresDifficulty: TextView
    private lateinit var i3choresWhoIsDoingItText: TextInputEditText
    private lateinit var i3choresPriorityText: TextView
    private lateinit var i3choresAddedByText: TextView

    companion object {
        private const val TAG = "TestHousemateActyTAG"
        private const val GENERAL_COLLECTION = "generalCollection"
        private const val GROUPS_DOC = "groupIDs"
        private const val CLIENTS_DOC = "clientIDs"
        private const val SHOPPING_LIST = "shoppingList"
        private const val SHOPPING_ITEMS_COLLECTION = "Shopping items"
        private const val CHORES_LIST = "choresList"
        private const val CHORE_ITEMS_COLLECTION = "Chore items"

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

        buttonClickListeners()
    }

    // todo: delete these
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
//        val theEditText: EditText = findViewById(R.id.edit_text_test)
        var retrievedDocDataMap: HashMap<String, Any>? = null
        db.collection("data").document("one")
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    retrievedDocDataMap = document.data as HashMap<String, Any>
                    val thePie = retrievedDocDataMap!!["numberExample"]
//                    theEditText.setText(thePie.toString())
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
    private fun buttonClickListeners() {
//            val testString = editTextTest.text//.toString()
//            Log.d(TAG, "$testString")
//            testTheDb()     //
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
            .collection(SHOPPING_ITEMS_COLLECTION).document(itemName)
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
            .collection(CHORE_ITEMS_COLLECTION).document(itemName)
            .set(choresItemData)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {

        i1shoppingItIsDone = findViewById(R.id.i1shopping_it_is_done)
        i1shoppingItemQty = findViewById(R.id.i1shopping_item_qty)
        i1shoppingItemName = findViewById(R.id.i1shopping_item_name)
        i1shoppingWhenNeededDoneText = findViewById(R.id.i1shopping_when_needed_done_text)
        i1shoppingWhereText = findViewById(R.id.i1shopping_where_text)
        i1shoppingCostText = findViewById(R.id.i1shopping_cost_text)
        i1shoppingWhoIsGettingItText = findViewById(R.id.i1shopping_who_is_getting_it_text)
        i1shoppingPriorityText = findViewById(R.id.i1shopping_priority_text)
        i1shoppingAddedByText = findViewById(R.id.i1shopping_added_by_text)

        i2shoppingItIsDone = findViewById(R.id.i2shopping_it_is_done)
        i2shoppingItemQty = findViewById(R.id.i2shopping_item_qty)
        i2shoppingItemName = findViewById(R.id.i2shopping_item_name)
        i2shoppingWhenNeededDoneText = findViewById(R.id.i2shopping_when_needed_done_text)
        i2shoppingWhereText = findViewById(R.id.i2shopping_where_text)
        i2shoppingCostText = findViewById(R.id.i2shopping_cost_text)
        i2shoppingWhoIsGettingItText = findViewById(R.id.i2shopping_who_is_getting_it_text)
        i2shoppingPriorityText = findViewById(R.id.i2shopping_priority_text)
        i2shoppingAddedByText = findViewById(R.id.i2shopping_added_by_text)

        i3shoppingItIsDone = findViewById(R.id.i3shopping_it_is_done)
        i3shoppingItemQty = findViewById(R.id.i3shopping_item_qty)
        i3shoppingItemName = findViewById(R.id.i3shopping_item_name)
        i3shoppingWhenNeededDoneText = findViewById(R.id.i3shopping_when_needed_done_text)
        i3shoppingWhereText = findViewById(R.id.i3shopping_where_text)
        i3shoppingCostText = findViewById(R.id.i3shopping_cost_text)
        i3shoppingWhoIsGettingItText = findViewById(R.id.i3shopping_who_is_getting_it_text)
        i3shoppingPriorityText = findViewById(R.id.i3shopping_priority_text)
        i3shoppingAddedByText = findViewById(R.id.i3shopping_added_by_text)

        i1choresItIsDone = findViewById(R.id.i1chores_it_is_done)
        i1choresItemName = findViewById(R.id.i1chores_item_name)
        i1choresWhenNeededDoneText = findViewById(R.id.i1chores_when_needed_done_text)
        i1choresDifficulty = findViewById(R.id.i1chores_difficulty)
        i1choresWhoIsDoingItText = findViewById(R.id.i1chores_who_is_doing_it_text)
        i1choresPriorityText = findViewById(R.id.i1chores_priority_text)
        i1choresAddedByText = findViewById(R.id.i1chores_added_by_text)

        i2choresItIsDone = findViewById(R.id.i2chores_it_is_done)
        i2choresItemName = findViewById(R.id.i2chores_item_name)
        i2choresWhenNeededDoneText = findViewById(R.id.i2chores_when_needed_done_text)
        i2choresDifficulty = findViewById(R.id.i2chores_difficulty)
        i2choresWhoIsDoingItText = findViewById(R.id.i2chores_who_is_doing_it_text)
        i2choresPriorityText = findViewById(R.id.i2chores_priority_text)
        i2choresAddedByText = findViewById(R.id.i2chores_added_by_text)

        i3choresItIsDone = findViewById(R.id.i3chores_it_is_done)
        i3choresItemName = findViewById(R.id.i3chores_item_name)
        i3choresWhenNeededDoneText = findViewById(R.id.i3chores_when_needed_done_text)
        i3choresDifficulty = findViewById(R.id.i3chores_difficulty)
        i3choresWhoIsDoingItText = findViewById(R.id.i3chores_who_is_doing_it_text)
        i3choresPriorityText = findViewById(R.id.i3chores_priority_text)
        i3choresAddedByText = findViewById(R.id.i3chores_added_by_text)
    }
}
