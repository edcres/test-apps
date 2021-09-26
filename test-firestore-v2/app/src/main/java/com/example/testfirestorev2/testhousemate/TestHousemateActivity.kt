package com.example.testfirestorev2.testhousemate

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.*
import com.example.testfirestorev2.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

// NOTICE: for the app to work, the DB has to be already set up with at least 1 group

// todo:
// -display the groupID somewhere in the UI
// -make/get client group id
// -make/get client id
// - for authentication
// start this database with a '00000000asdfg' groupID before users start using the app

class TestHousemateActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var exitGroupBtn: Button
    private lateinit var toAddItemActivityBtn: Button
    private lateinit var groupIDCollectionDB: CollectionReference

    private lateinit var sharedPref: SharedPreferences
    private val mainSharedPrefTag = "TestHousemateActySP"
    private val groupIdSPTag = "Group ID"
    private val clientIdSPTag = "Client ID"

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
        const val TAG = "TestHousemateActyTAG"

        const val GENERAL_COLLECTION = "General Collection"
        const val GROUP_IDS_DOC = "Group IDs"
        const val CLIENT_IDS_DOC = "Client IDs"
        const val SHOPPING_LIST_DOC = "Shopping List"
        const val SHOPPING_ITEMS_COLLECTION = "Shopping Items"
        const val CHORES_LIST_DOC = "Chores List"
        const val CHORE_ITEMS_COLLECTION = "Chore Items"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_housemate)

        sharedPref = this.getSharedPreferences(mainSharedPrefTag, Context.MODE_PRIVATE)
        setUpDatabaseIDsAndFetchData()
        bindWidgetIDs()
        populateUIWidgetsList()
        widgetEventListeners()
    }

    // CLICK LISTENERS //
    @SuppressLint("ApplySharedPref")
    private fun widgetEventListeners() {
        val completedToastMessage = "completed"
        exitGroupBtn.setOnClickListener {
            // deletes: groupID, clientID, and addedBy name
            clearSP(sharedPref)
            displayToastMessage(this, "Removed from group")
        }
        toAddItemActivityBtn.setOnClickListener {
            val goToAddItem = Intent(this, AddItemActivity::class.java)
            startActivity(goToAddItem)
        }
        //completed
        i1shoppingItIsDone.setOnClickListener {
            if (i1shoppingItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeShoppingItemsNames.size > 0) {
                sendCompletionInputToDb(
                    SHOPPING_LIST_DOC,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[0],
                    i1shoppingItIsDone.isChecked
                )
            }
        }
        i2shoppingItIsDone.setOnClickListener {
            if (i2shoppingItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeShoppingItemsNames.size > 1) {
                sendCompletionInputToDb(
                    SHOPPING_LIST_DOC,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[1],
                    i2shoppingItIsDone.isChecked
                )
            }
        }
        i3shoppingItIsDone.setOnClickListener {
            if (i3shoppingItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeShoppingItemsNames.size > 2) {
                sendCompletionInputToDb(
                    SHOPPING_LIST_DOC,
                    SHOPPING_ITEMS_COLLECTION,
                    threeShoppingItemsNames[2],
                    i3shoppingItIsDone.isChecked
                )
            }
        }
        i1choresItIsDone.setOnClickListener {
            if (i1choresItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeChoreItemsNames.size > 0) {
                sendCompletionInputToDb(
                    CHORES_LIST_DOC,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[0],
                    i1choresItIsDone.isChecked
                )
            }
        }
        i2choresItIsDone.setOnClickListener {
            if (i2choresItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeChoreItemsNames.size > 1) {
                sendCompletionInputToDb(
                    CHORES_LIST_DOC,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[1],
                    i2choresItIsDone.isChecked
                )
            }
        }
        i3choresItIsDone.setOnClickListener {
            if (i3choresItIsDone.isChecked) {
                displayToastMessage(this, completedToastMessage)
            }
            if (threeChoreItemsNames.size > 2) {
                sendCompletionInputToDb(
                    CHORES_LIST_DOC,
                    CHORE_ITEMS_COLLECTION,
                    threeChoreItemsNames[2],
                    i3choresItIsDone.isChecked
                )
            }
        }
        // volunteer
        i1shoppingWhoIsGettingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (threeShoppingItemsNames.size > 0) {
                    sendVolunteerInputToDb(
                        SHOPPING_LIST_DOC,
                        SHOPPING_ITEMS_COLLECTION,
                        threeShoppingItemsNames[0],
                        i1shoppingWhoIsGettingItText.text.toString()
                    )
                }
                true
            } else {
                false
            }
        }
        i2shoppingWhoIsGettingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (threeShoppingItemsNames.size > 1) {
                    sendVolunteerInputToDb(
                        SHOPPING_LIST_DOC,
                        SHOPPING_ITEMS_COLLECTION,
                        threeShoppingItemsNames[1],
                        i2shoppingWhoIsGettingItText.text.toString()
                    )
                }
                true
            } else {
                false
            }
        }
        i3shoppingWhoIsGettingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (threeShoppingItemsNames.size > 2) {
                    sendVolunteerInputToDb(
                        SHOPPING_LIST_DOC,
                        SHOPPING_ITEMS_COLLECTION,
                        threeShoppingItemsNames[2],
                        i3shoppingWhoIsGettingItText.text.toString()
                    )
                }
                true
            } else {
                false
            }
        }

        i1choresWhoIsDoingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (threeChoreItemsNames.size > 0) {
                    sendVolunteerInputToDb(
                        CHORES_LIST_DOC,
                        CHORE_ITEMS_COLLECTION,
                        threeChoreItemsNames[0],
                        i1choresWhoIsDoingItText.text.toString()
                    )
                }
                true
            } else {
                false
            }
        }
        i2choresWhoIsDoingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                if (threeChoreItemsNames.size > 1) {
                    sendVolunteerInputToDb(
                        CHORES_LIST_DOC,
                        CHORE_ITEMS_COLLECTION,
                        threeChoreItemsNames[1],
                        i2choresWhoIsDoingItText.text.toString()
                    )
                }

                true
            } else {
                false
            }
        }
        i3choresWhoIsDoingItText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                if (threeChoreItemsNames.size > 2) {
                    sendVolunteerInputToDb(
                        CHORES_LIST_DOC,
                        CHORE_ITEMS_COLLECTION,
                        threeChoreItemsNames[2],
                        i3choresWhoIsDoingItText.text.toString()
                    )
                }

                true
            } else {
                false
            }
        }
    }
    // CLICK LISTENERS //
    // HELPER FUNCTIONS //
    private fun populateUIWidgetsList() {
        showGroupIdOnBtn()
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
                val neededByText = "by ${threeShoppingItems[i][NEEDED_BY_FIELD].toString()}"
                val whereText = "at ${threeShoppingItems[i][PURCHASE_LOCATION_FIELD].toString()}"
                val costText = "cost: ${threeShoppingItems[i][PRIORITY_FIELD].toString()}"
                val priorityText = "priority ${threeShoppingItems[i][PRIORITY_FIELD].toString()}"
                val addedByText = "added by ${threeShoppingItems[i][ADDED_BY_FIELD].toString()}"
                shoppingItIsDoneList[i].isChecked = threeShoppingItems[i][COMPLETED_FIELD] as Boolean
                shoppingItemQtyList[i].text = threeShoppingItems[i][QUANTITY_FIELD].toString()
                shoppingItemNameList[i].text = threeShoppingItems[i][NAME_FIELD].toString()
                shoppingWhenNeededDoneTextList[i].text = neededByText
                shoppingWhereTextList[i].text = whereText
                shoppingCostTextList[i].text = costText
                shoppingWhoIsGettingItTextList[i]
                    .setText(threeShoppingItems[i][VOLUNTEER_FIELD].toString())
                shoppingPriorityTextList[i].text = priorityText
                shoppingAddedByTextList[i].text = addedByText
            }
        }
        //chores
        for (i in 0 until threeChoreItems.size) {
            if (i < 3) {
                val whenNeededDoneText = "by ${threeChoreItems[i][NEEDED_BY_FIELD].toString()}"
                val difficultyChoice = "difficulty: ${threeChoreItems[i][DIFFICULTY_FIELD].toString()}"
                val priorityText = "priority ${threeChoreItems[i][PRIORITY_FIELD].toString()}"
                val addedByText = "added by ${threeChoreItems[i][ADDED_BY_FIELD].toString()}"
                choresItIsDoneList[i].isChecked =
                    threeChoreItems[i][COMPLETED_FIELD] as Boolean
                choresItemNameList[i].text = threeChoreItems[i][NAME_FIELD].toString()
                choresWhenNeededDoneTextList[i].text = whenNeededDoneText
                choresDifficultyList[i].text = difficultyChoice
                choresWhoIsDoingItTextList[i].setText(threeChoreItems[i][VOLUNTEER_FIELD].toString())
                choresPriorityTextList[i].text = priorityText
                choresAddedByTextList[i].text = addedByText
            }
        }
    }

    private fun makeDialogBoxAndSetGroupID() {
        val nameInputDialog = MaterialAlertDialogBuilder(this)
        val customAlertDialogView = LayoutInflater.from(this)
            .inflate(R.layout.name_dialog_box, null, false)
        val inputNameDialog: EditText = customAlertDialogView.findViewById(R.id.input_name_dialog)
        nameInputDialog.setView(customAlertDialogView)
            .setTitle("Your group ID")
            .setPositiveButton("Accept") { dialog, _ ->
                clientGroupIDCollection = inputNameDialog.text.toString()
                Log.d(TAG, "makeDialogBoxAndSetGroupID: accept clicked $clientGroupIDCollection")
                sendDataToSP(sharedPref, groupIdSPTag, clientGroupIDCollection!!)
                get3ItemsFromDB()
                populateTheListItemsUI()
                showGroupIdOnBtn()
                dialog.dismiss()
            }
            .setNegativeButton("New Group") { dialog, _ ->
                Log.d(TAG, "makeDialogBoxAndSetGroupID: negative button called")
                generateClientGroupID()
                dialog.dismiss()
            }
            .show()
    }

    private fun get3ItemsFromDB() {
        Log.d(TAG, "get3ItemsFromDB: called")
        groupIDCollectionDB = db.collection(GENERAL_COLLECTION).document(GROUP_IDS_DOC)
            .collection(clientGroupIDCollection!!)
        Log.d(TAG, "get3ItemsFromDB: groupID to fetch: $clientGroupIDCollection")
        groupIDCollectionDB.document(CLIENT_IDS_DOC).get()
            .addOnSuccessListener { result ->
                // This db query is probably unnecessary , I'm doing 2 more specific
                //  queries with this, and I'm actually using fetched data from those
                Log.d(TAG, "get3ItemsFromDB: ID = $clientGroupIDCollection")
                if (result.data == null) {
                    // the group doesn't exists
                    displayToastMessage(this, "Group ID not found")
                    makeDialogBoxAndSetGroupID()
                } else {
                    Log.d(TAG, "get3ItemsFromDB: groupID found")
                    // the group does exist
                    // add shopping items
                    groupIDCollectionDB.document(SHOPPING_LIST_DOC)
                        .collection(SHOPPING_ITEMS_COLLECTION)
                        .get()
                            .addOnSuccessListener { shoppingResult ->
                            Log.d(TAG, "get3ItemsFromDB: shopping called")
                            for (document in shoppingResult) {
                                // add 3 items to the list and exit the get call
                                if (threeShoppingItems.size < 4) {
                                    val thisItem = document.data as MutableMap<String, Any>
                                    threeShoppingItemsNames.add(thisItem[NAME_FIELD] as String)
                                    threeShoppingItems.add(thisItem)
                                    // this happens twice, probably concurrently
                                    setUpRealtimeFetching()
                                }
                            }
                        }
                        .addOnFailureListener{ e ->
                            Log.d(TAG, "Error getting documents: shopping ", e)
                        }
                    // add chore items
                    groupIDCollectionDB.document(CHORES_LIST_DOC)
                        .collection(CHORE_ITEMS_COLLECTION)
                        .get()
                        .addOnSuccessListener { choresResult ->
                            for (document in choresResult) {
                                Log.d(TAG, "get3ItemsFromDB: chores called")
                                // add 3 items to the list and exit the get call
                                if (threeChoreItems.size < 4) {
                                    val thisItem = document.data as MutableMap<String, Any>
                                    threeChoreItemsNames.add(thisItem[NAME_FIELD] as String)
                                    threeChoreItems.add(thisItem)
                                    // this happens twice, probably concurrently
                                    setUpRealtimeFetching()
                                }
                            }
                        }
                        .addOnFailureListener{ e ->
                            Log.d(TAG, "Error fetching data: ", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "get3ItemsFromDB: db fetch failed", e)
            }
    }

    private fun sendCompletionInputToDb(
        itemList: String,
        itemCollection: String,
        docName: String,
        completed: Boolean) {
        // when and item is completed, let the db know
        groupIDCollectionDB.document(itemList)
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
        volunteerName: String
    ) {
        groupIDCollectionDB.document(itemList)
            .collection(itemCollection)
            .document(docName)
            .update(VOLUNTEER_FIELD, volunteerName)
            .addOnSuccessListener {
                displayToastMessage(this, "volunteer changed")
            }
    }

    private fun generateClientGroupID() {
        Log.d("Acty", "generateClientGroupID: called")
        val lastGroupAddedField = "last group added"
        var oldID: String
        // ie. 00000001asdfg, 00000002fagsd, 00000003sgdfa ...
        // Get the latest groupID from the remote db (ie. 00000001asdfg)
        db.collection(GENERAL_COLLECTION).document(GROUP_IDS_DOC)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val groupIdDoc = document.data as Map<String, Any>
                    oldID = groupIdDoc[lastGroupAddedField] as String
                    clientGroupIDCollection = add1AndScrambleLetters(oldID)
                    // Add the new id to the database as the last id added
                    Log.d(TAG, "generateClientGroupID: $clientGroupIDCollection")
                    db.collection(GENERAL_COLLECTION)
                        .document(GROUP_IDS_DOC)
                        .update(lastGroupAddedField, clientGroupIDCollection)
                        .addOnSuccessListener {
                            Log.d("Acty","generateClientGroupID: lastGroupAddedField updated")
                            sendDataToSP(sharedPref, groupIdSPTag, clientGroupIDCollection!!)
                            getClientID()
                        }
                        .addOnFailureListener { e ->
                            Log.d("DB Query", "Error updating groupIDs doc", e)
                        }
                } else {
                    Log.d(TAG, "generateClientGroupID: groupIDs document is null")
                }
            }
            .addOnFailureListener{e->Log.d(TAG,"generateClientGroupID: database fetch failed",e)}
    }
    private fun getClientID() {
        Log.d(TAG, "getClientID: called")
        // try to get the clientID from shared preferences
        clientIDCollection = getDataFromSP(sharedPref, clientIdSPTag)
        // you need a groupID to have a clientID
        if(clientIDCollection == null) {
            if(clientGroupIDCollection != null) {
                generateClientID(clientGroupIDCollection!!)
            }
        }
    }
    private fun generateClientID(groupID: String) {
        val lastClientAddedField = "last client added"
        var oldID: String
        var newID: String?
        // "$groupID + 00000001asdfg"
        val clientsDocDb = db.collection(GENERAL_COLLECTION).document(GROUP_IDS_DOC)
            .collection(groupID).document(CLIENT_IDS_DOC)
        // get the latest clientID from the db, set 'oldID' to this
        clientsDocDb.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    // If there's already another user
                    val clientIdDoc = document.data as Map<String, Any>
                    oldID = clientIdDoc[lastClientAddedField] as String
                    newID = add1AndScrambleLetters(oldID)
                    // Add the new id to the database as the last id added
                    clientsDocDb.update(lastClientAddedField, newID)
                        .addOnSuccessListener {
                            clientIDCollection = newID
                            sendDataToSP(sharedPref, groupIdSPTag, clientIDCollection!!)
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
                            Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                            clientIDCollection = newID
                            sendDataToSP(sharedPref, clientIdSPTag, clientIDCollection!!)
                        }
                        .addOnFailureListener{e->Log.w(ContentValues.TAG, "Error writing document",e)}
                }
            }
    }
    private fun showGroupIdOnBtn() {
        if (clientGroupIDCollection != null) {
            val exitGroupBtnText = "${exitGroupBtn.text} $clientGroupIDCollection"
            exitGroupBtn.text = exitGroupBtnText
        }
    }
    // HELPER FUNCTIONS //
    // SETUP FUNCTIONS //
    private fun setUpDatabaseIDsAndFetchData() {
        // try to get the groupId from shared preferences
        clientGroupIDCollection = getDataFromSP(sharedPref, groupIdSPTag)
        // if null, in a dialog box ask user what their groupID is,
        if(clientGroupIDCollection == null) {
            makeDialogBoxAndSetGroupID()
        } else {
            get3ItemsFromDB()
            populateTheListItemsUI()
            getClientID()
        }
    }

    private fun setUpRealtimeFetching() {
        Log.d(TAG, "setUpRealtimeFetching: called")
        // get 3 items in shopping list
        for (i in 0 until threeShoppingItems.size) {
            groupIDCollectionDB.document(SHOPPING_LIST_DOC)
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
                        Log.d(
                            TAG, "setUpRealtimeFetching: " +
                                "${threeShoppingItemsNames[i]} fetch successful.")
                    } else {
                        Log.d(TAG, "setUpRealtimeFetching: Data is null.")
                    }
                }
        }
        // get 3 items in chores list
        for (i in 0 until threeChoreItems.size) {
            groupIDCollectionDB.document(CHORES_LIST_DOC)
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
                        Log.d(
                            TAG, "setUpRealtimeFetching: " +
                                "${threeChoreItemsNames[i]} fetch successful.")
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

    private fun bindWidgetIDs() {
        exitGroupBtn = findViewById(R.id.exit_group_btn)
        toAddItemActivityBtn = findViewById(R.id.to_add_item_activity_btn)

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
    // SETUP FUNCTIONS //
}
