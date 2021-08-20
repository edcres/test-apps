package com.example.testfirestorev2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// todo:
// -completed, volunteer, added by
// -make/get client group id
// -make/get client id

class TestHousemateActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var toAddItemActivity: Button
    private lateinit var clientIDCollectionDB: CollectionReference

    private var threeShoppingItemsNames = mutableListOf<String>()
    private var threeChoreItemsNames = mutableListOf<String>()
    private var threeShoppingItems = mutableListOf<MutableMap<String,Any>>()
    private var threeChoreItems = mutableListOf<MutableMap<String,Any>>()

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

    private lateinit var shoppingItIsDoneList: List<CheckBox>
    private lateinit var shoppingItemQtyList: List<TextView>
    private lateinit var shoppingItemNameList: List<TextView>
    private lateinit var shoppingWhenNeededDoneTextList: List<TextView>
    private lateinit var shoppingWhereTextList: List<TextView>
    private lateinit var shoppingCostTextList: List<TextView>
    private lateinit var shoppingWhoIsGettingItTextList: List<TextInputEditText>
    private lateinit var shoppingPriorityTextList: List<TextView>
    private lateinit var shoppingAddedByTextList: List<TextView>

    private lateinit var choresItIsDoneList: List<CheckBox>
    private lateinit var choresItemNameList: List<TextView>
    private lateinit var choresWhenNeededDoneTextList: List<TextView>
    private lateinit var choresDifficultyList: List<TextView>
    private lateinit var choresWhoIsDoingItTextList: List<TextInputEditText>
    private lateinit var choresPriorityTextList: List<TextView>
    private lateinit var choresAddedByTextList: List<TextView>

    companion object {
        var clientGroupIDCollection = "abcd1234"
        var clientIDCollection = "${clientGroupIDCollection}abcd1234"

        const val TAG = "TestHousemateActyTAG"
        const val GENERAL_COLLECTION = "generalCollection"
        const val GROUPS_DOC = "groupIDs"
        const val CLIENTS_DOC = "clientIDs"
        const val SHOPPING_LIST = "shoppingList"
        const val SHOPPING_ITEMS_COLLECTION = "shoppingItems"
        const val CHORES_LIST = "choresList"
        const val CHORE_ITEMS_COLLECTION = "choreItems"

        const val NAME_FIELD = "name"
        const val QUANTITY_FIELD = "quantity"
        const val ADDED_BY_FIELD = "added_by"
        const val COMPLETED_FIELD = "completed"
        const val COST_FIELD = "cost"
        const val PURCHASE_LOCATION_FIELD = "purchase_location"
        const val NEEDED_BY_FIELD = "needed_by"
        const val VOLUNTEER_FIELD = "volunteer"
        const val PRIORITY_FIELD = "priority"
        const val DIFFICULTY_FIELD = "difficulty"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_housemate)

        bindWidgetIDs()
        widgetEventListeners()
        populateUIWidgetsList()
        get3ItemsFromDB()
        populateTheListItemsUI()
    }

    // SETUP FUNCTIONS //
    private fun setUpRealtimeFetching() {
        Log.d(TAG, "setUpRealtimeFetching: called")
        // get 3 items in shopping list
        Log.d(TAG, "setUpRealtimeFetching: threeShoppingItems size: ${threeShoppingItems.size}")
        for (i in 0 until threeShoppingItems.size) {
            Log.d(TAG, "setUpRealtimeFetching: shopping loop called i = $i || size = ${threeShoppingItems.size}")
            clientIDCollectionDB.document(SHOPPING_LIST)
                .collection(SHOPPING_ITEMS_COLLECTION)
                .document(threeShoppingItemsNames[i])
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.d(TAG, "setUpRealtimeFetching: DB Listen Fail in shopping.", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        // Get 3 item maps from db and set them to threeShoppingItems
                        threeShoppingItems[i] = snapshot.data as HashMap<String, Any>
                        populateTheListItemsUI()
                        Log.d(TAG, "setUpRealtimeFetching: ${threeShoppingItemsNames[i]} fetch successful.")
                    } else {
                        Log.d(TAG, "setUpRealtimeFetching: Data is null.")
                    }
                }
        }
        // get 3 items in chores list
        for (i in 0 until threeChoreItems.size) {
            Log.d(TAG, "setUpRealtimeFetching: shopping loop called i = $i || size = ${threeChoreItems.size}")
            clientIDCollectionDB.document(CHORES_LIST)
                .collection(CHORE_ITEMS_COLLECTION)
                .document(threeChoreItemsNames[i])
                .addSnapshotListener { snapshot, e ->

                    if (e != null) {
                        Log.d(TAG, "setUpRealtimeFetching: DB Listen Fail in chores.", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        // get 3 item maps from db and set them to threeChoreItems
                        threeChoreItems[i] = snapshot.data as HashMap<String, Any>
                        populateTheListItemsUI()
                        Log.d(TAG, "setUpRealtimeFetching: ${threeChoreItemsNames[i]} fetch successful.")
                    } else {
                        Log.d(TAG, "setUpRealtimeFetching: Data is null.")
                    }

                }
        }
        // todo: if I keep going with the code below, it'll replace the 2 for loops above,
        //  but it's more complicated and hard to read.
//        val itemNamesLists = listOf<MutableList<String>>(threeShoppingNames, threeChoreNames)
//        val listsDocuments = listOf<String>(SHOPPING_LIST, CHORES_LIST)
//        val listsCollections = listOf<String>(SHOPPING_ITEMS_COLLECTION, CHORE_ITEMS_COLLECTION)
//        for (i in 0 until itemNamesLists.size) {
//            for (item in threeShoppingNames) {
//                clientIDCollectionDB.document(listsDocuments[i])
//                    .collection(listsCollections[i]).document(item)
//                    .addSnapshotListener { snapshot, e ->
//                        if (e != null) {
//                            Log.d(TAG, "setUpRealtimeFetching: DB Listen Fail.", e)
//                            return@addSnapshotListener
//                        }
//                        if (snapshot != null && snapshot.exists()) {
//                            Log.d(TAG, "setUpRealtimeFetching: Fetch successful.")
//                        } else {
//                            Log.d(TAG, "setUpRealtimeFetching: Data is null.")
//                        }
//                    }
//            }
//        }
    }

    // SETUP FUNCTIONS //
    private fun get3ItemsFromDB() {
        clientIDCollectionDB = db.collection(GENERAL_COLLECTION).document(GROUPS_DOC)
            .collection(clientGroupIDCollection).document(CLIENTS_DOC)
            .collection(clientIDCollection)
        // add shopping items
        clientIDCollectionDB.document(SHOPPING_LIST)
            .collection(SHOPPING_ITEMS_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "get3ItemsFromDB: shopping called")
                    // add 3 items to the list and exit the get call
                    if (threeShoppingItems.size < 4) {
                        val thisItem = document.data as MutableMap<String, Any>
                        threeShoppingItemsNames.add(thisItem[NAME_FIELD] as String)
                        threeShoppingItems.add(thisItem)
                        setUpRealtimeFetching()         // todo try to not repeat this 2ice
                    } else {
                        return@addOnSuccessListener
                    }
                }
            }
            .addOnFailureListener{ e ->
                Log.d(TAG, "Error getting documents: shopping ", e)
            }

        // add chore items
        clientIDCollectionDB.document(CHORES_LIST)
            .collection(CHORE_ITEMS_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "get3ItemsFromDB: chores called")
                    // add 3 items to the list and exit the get call
                    if (threeChoreItems.size < 4) {
                        val thisItem = document.data as MutableMap<String, Any>
                        threeChoreItemsNames.add(thisItem[NAME_FIELD] as String)
                        threeChoreItems.add(thisItem)
                        setUpRealtimeFetching()         // todo try to not repeat this 2ice
                    } else {
                        return@addOnSuccessListener
                    }
                }
            }
            .addOnFailureListener{ e ->
                Log.d(TAG, "Error getting documents: ", e)
            }
    }

    // CLICK LISTENERS //
    private fun widgetEventListeners() {
        toAddItemActivity.setOnClickListener {
            val goToAddItem = Intent(this, AddItemActivity::class.java)
            startActivity(goToAddItem)
        }
        i1shoppingItIsDone.setOnClickListener {
            if (threeShoppingItemsNames.size > 0) {
                sendCompletionInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[0],
                    i1shoppingItIsDone.isChecked
                )
            }
        }
        i2shoppingItIsDone.setOnClickListener {
            if (threeShoppingItemsNames.size > 1) {
                sendCompletionInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[1],
                    i2shoppingItIsDone.isChecked
                )
            }
        }
        i3shoppingItIsDone.setOnClickListener {
            if (threeShoppingItemsNames.size > 2) {
                sendCompletionInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[2],
                    i3shoppingItIsDone.isChecked
                )
            }
        }
        i1choresItIsDone.setOnClickListener {
            if (threeChoreItemsNames.size > 0) {
                sendCompletionInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[0],
                    i1choresItIsDone.isChecked
                )
            }
        }
        i2choresItIsDone.setOnClickListener {
            if (threeChoreItemsNames.size > 1) {
                sendCompletionInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[1],
                    i2choresItIsDone.isChecked
                )
            }
        }
        i3choresItIsDone.setOnClickListener {
            if (threeChoreItemsNames.size > 2) {
                sendCompletionInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[2],
                    i3choresItIsDone.isChecked
                )
            }
        }
        // todo: possible bug: if user deletes everything in the box, value might be null
        // todo: possible bug: if user types too fast, app might crash bc of concurrency issues
        i1shoppingWhoIsGettingItText.doAfterTextChanged {
            if (threeShoppingItemsNames.size > 0) {
                sendVolunteerInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[0],
                    i1shoppingWhoIsGettingItText.text.toString()
                )
            }
        }
        i2shoppingWhoIsGettingItText.doAfterTextChanged {
            if (threeShoppingItemsNames.size > 1) {
                sendVolunteerInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[1],
                    i2shoppingWhoIsGettingItText.text.toString()
                )
            }
        }
        i3shoppingWhoIsGettingItText.doAfterTextChanged {
            if (threeShoppingItemsNames.size > 2) {
                sendVolunteerInputToDb(
                    SHOPPING_LIST,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[2],
                    i3shoppingWhoIsGettingItText.text.toString()
                )
            }
        }
        i1choresWhoIsDoingItText.doAfterTextChanged {
            if (threeChoreItemsNames.size > 0) {
                sendVolunteerInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeShoppingItemsNames[0],
                    i1choresWhoIsDoingItText.text.toString()
                )
            }
        }
        i2choresWhoIsDoingItText.doAfterTextChanged {
            if (threeChoreItemsNames.size > 1) {
                sendVolunteerInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeShoppingItemsNames[1],
                    i2choresWhoIsDoingItText.text.toString()
                )
            }
        }
        i3choresWhoIsDoingItText.doAfterTextChanged {
            if (threeChoreItemsNames.size > 2) {
                sendVolunteerInputToDb(
                    CHORES_LIST,
                    CHORE_ITEMS_COLLECTION,
                    threeShoppingItemsNames[2],
                    i3choresWhoIsDoingItText.text.toString()
                )
            }
        }
    }

    // HELPER FUNCTIONS //
    private fun populateUIWidgetsList() {
        shoppingItIsDoneList = listOf(i1shoppingItIsDone, i2shoppingItIsDone, i3shoppingItIsDone)
        shoppingItemQtyList = listOf(i1shoppingItemQty, i2shoppingItemQty, i3shoppingItemQty)
        shoppingItemNameList = listOf(i1shoppingItemName, i2shoppingItemName, i3shoppingItemName)
        shoppingWhenNeededDoneTextList = listOf(i1shoppingWhenNeededDoneText,
            i2shoppingWhenNeededDoneText, i3shoppingWhenNeededDoneText)
        shoppingWhereTextList = listOf(i1shoppingWhereText, i2shoppingWhereText, i3shoppingWhereText)
        shoppingCostTextList = listOf(i1shoppingCostText, i2shoppingCostText, i3shoppingCostText)
        shoppingWhoIsGettingItTextList = listOf(i1shoppingWhoIsGettingItText,
            i2shoppingWhoIsGettingItText, i3shoppingWhoIsGettingItText)
        shoppingPriorityTextList = listOf(i1shoppingPriorityText, i2shoppingPriorityText,
            i3shoppingPriorityText)
        shoppingAddedByTextList = listOf(i1shoppingAddedByText, i2shoppingAddedByText,
            i3shoppingAddedByText)

        choresItIsDoneList = listOf(i1choresItIsDone, i2choresItIsDone, i3choresItIsDone)
        choresItemNameList = listOf(i1choresItemName, i2choresItemName, i3choresItemName)
        choresWhenNeededDoneTextList = listOf(i1choresWhenNeededDoneText,
            i2choresWhenNeededDoneText, i3choresWhenNeededDoneText)
        choresDifficultyList = listOf(i1choresDifficulty, i2choresDifficulty, i3choresDifficulty)
        choresWhoIsDoingItTextList = listOf(i1choresWhoIsDoingItText, i2choresWhoIsDoingItText,
            i3choresWhoIsDoingItText)
        choresPriorityTextList = listOf(i1choresPriorityText, i2choresPriorityText,
            i3choresPriorityText)
        choresAddedByTextList = listOf(i1choresAddedByText, i2choresAddedByText, i3choresAddedByText)
    }

    private fun populateTheListItemsUI() {
        // shopping
        for (i in 0 until threeShoppingItems.size) {
            if (i < 3) {
                shoppingItIsDoneList[i].isChecked =
                    threeShoppingItems[i][COMPLETED_FIELD] as Boolean // should give a boolean value
                shoppingItemQtyList[i].text = threeShoppingItems[i][QUANTITY_FIELD].toString()
                shoppingItemNameList[i].text = threeShoppingItems[i][NAME_FIELD].toString()
                shoppingWhenNeededDoneTextList[i].text =
                    "by ${threeShoppingItems[i][NEEDED_BY_FIELD].toString()}"
                shoppingWhereTextList[i].text =
                    "at ${threeShoppingItems[i][PURCHASE_LOCATION_FIELD].toString()}"
                shoppingCostTextList[i].text =
                    "cost: ${threeShoppingItems[i][PRIORITY_FIELD].toString()}"
                shoppingWhoIsGettingItTextList[i]
                    .setText(threeShoppingItems[i][VOLUNTEER_FIELD].toString())
                shoppingPriorityTextList[i].text =
                    "priority ${threeShoppingItems[i][PRIORITY_FIELD].toString()}"
                shoppingAddedByTextList[i].text =
                    "added by ${threeShoppingItems[i][ADDED_BY_FIELD].toString()}"
            }
        }
        //chores
        for (i in 0 until threeChoreItems.size) {
            if (i < 3) {
                choresItIsDoneList[i].isChecked =
                    threeChoreItems[i][COMPLETED_FIELD] as Boolean // should give a boolean value
                choresItemNameList[i].text = threeChoreItems[i][NAME_FIELD].toString()
                choresWhenNeededDoneTextList[i].text =
                    "by ${threeChoreItems[i][NEEDED_BY_FIELD].toString()}"
                choresDifficultyList[i].text =
                    "difficulty: ${threeChoreItems[i][DIFFICULTY_FIELD].toString()}"
                choresWhoIsDoingItTextList[i].setText(threeChoreItems[i][VOLUNTEER_FIELD].toString())
                choresPriorityTextList[i].text =
                    "priority ${threeChoreItems[i][PRIORITY_FIELD].toString()}"
                choresAddedByTextList[i].text =
                    "added by ${threeChoreItems[i][ADDED_BY_FIELD].toString()}"
            }
        }
    }

    private fun sendCompletionInputToDb(
        itemList: String,
        itemCollection: String,
        docName: String,
        completed: Boolean) {
        // when and item is completed, let the db know
        clientIDCollectionDB.document(itemList)
            .collection(itemCollection)
            .document(docName)
            .update(COMPLETED_FIELD, completed)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "Doc successfully updated!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating doc", e) }
    }

    private fun sendVolunteerInputToDb(
        itemList: String,
        itemCollection: String,
        docName: String,
        volunteerName: String) {
        clientIDCollectionDB.document(itemList)
            .collection(itemCollection)
            .document(docName)
            .update(VOLUNTEER_FIELD, volunteerName)
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        toAddItemActivity = findViewById(R.id.to_add_item_activity)

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
