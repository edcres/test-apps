package com.example.testfirestorev2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class AddItemActivity : AppCompatActivity() {

    private val sharedPreferenceTag = "TestHousemateActySP"
    private lateinit var sharedPref: SharedPreferences
    private val clientNameSPTag = "Client Name"
    private val homeActivity = TestHousemateActivity
    private lateinit var addItemButton: Button
    private val activityTAG = "AddItemActyTAG"
    private val testHousemateActivity = TestHousemateActivity
    private val db = Firebase.firestore
    private val clientIDCollectionDB = db.collection(testHousemateActivity.GENERAL_COLLECTION)
        .document(testHousemateActivity.GROUPS_DOC).collection(homeActivity.clientGroupIDCollection!!)

    private lateinit var i1shoppingItemQty: TextView
    private lateinit var i1shoppingItemName: TextView
    private lateinit var i1shoppingWhenNeededDoneText: TextView
    private lateinit var i1shoppingWhereText: TextView
    private lateinit var i1shoppingCostText: TextView
    private lateinit var i1shoppingPriorityText: RadioGroup
    private lateinit var i1shoppingPriority1: RadioButton
    private lateinit var i1shoppingPriority2: RadioButton
    private lateinit var i1shoppingPriority3: RadioButton

    private lateinit var i1choresItemName: TextView
    private lateinit var i1choresWhenNeededDoneText: TextView
    private lateinit var i1choresDifficulty: RadioGroup
    private lateinit var i1choresDifficulty1: RadioButton
    private lateinit var i1choresDifficulty2: RadioButton
    private lateinit var i1choresDifficulty3: RadioButton
    private lateinit var i1choresPriorityText: RadioGroup
    private lateinit var i1choresPriority1: RadioButton
    private lateinit var i1choresPriority2: RadioButton
    private lateinit var i1choresPriority3: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        initiateVars()
        bindUIWidgets()
        buttonsOnClick()
    }

    // HELPER FUNCTIONS //
    private fun addShoppingItem(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        neededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        val shoppingItemData = hashMapOf(
            homeActivity.NAME_FIELD to itemName,
            homeActivity.QUANTITY_FIELD to itemQuantity,
            homeActivity.COST_FIELD to itemCost,
            homeActivity.PURCHASE_LOCATION_FIELD to purchaseLocation,
            homeActivity.NEEDED_BY_FIELD to neededBy,
            homeActivity.PRIORITY_FIELD to itemPriority,
            homeActivity.COMPLETED_FIELD to false,
            homeActivity.VOLUNTEER_FIELD to "",
            homeActivity.ADDED_BY_FIELD to addedBy
        )
        // access the clientGroup, then the client, then the shopping item
        clientIDCollectionDB.document(testHousemateActivity.SHOPPING_LIST)
            .collection(testHousemateActivity.SHOPPING_ITEMS_COLLECTION).document(itemName)
            .set(shoppingItemData)
            .addOnSuccessListener { Log.d(activityTAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(activityTAG, "Error writing document", e) }
    }

    private fun addChoreItem(
        itemName: String,
        difficulty: Int,
        neededBy: String,   // try and make this a date
        itemPriority: Int,
        addedBy: String
    ) {
        val choresItemData = hashMapOf(
            homeActivity.NAME_FIELD to itemName,
            homeActivity.DIFFICULTY_FIELD to difficulty,
            homeActivity.NEEDED_BY_FIELD to neededBy,
            homeActivity.PRIORITY_FIELD to itemPriority,
            homeActivity.COMPLETED_FIELD to false,
            homeActivity.VOLUNTEER_FIELD to "",
            homeActivity.ADDED_BY_FIELD to addedBy
        )

        // access the clientGroup, then the client, then the shopping item
        clientIDCollectionDB.document(testHousemateActivity.CHORES_LIST)
            .collection(testHousemateActivity.CHORE_ITEMS_COLLECTION).document(itemName)
            .set(choresItemData)
            .addOnSuccessListener { Log.d(activityTAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(activityTAG, "Error writing document", e) }
    }

    @SuppressLint("ApplySharedPref")
    private fun sendNameOfClientToSP(clientName: String) {
        val spEditor: SharedPreferences.Editor = sharedPref.edit()
        spEditor.putString(clientNameSPTag, clientName).commit()
    }

    private fun getNameOfClientFromSP(): String? {
        return sharedPref.getString(clientNameSPTag, null)
    }

    private fun addItems(clientName: String?) {
        // add shopping item
        // check if the name is filled for each. Only send info if it's filled.
        if (i1shoppingItemName.text.toString() != "") {
            val shoppingPriority = when (true) {
                i1shoppingPriority1.isChecked -> 1
                i1shoppingPriority2.isChecked -> 2
                i1shoppingPriority1.isChecked -> 3
                else -> 0
            }
            addShoppingItem(
                i1shoppingItemName.text.toString(),
                i1shoppingItemQty.text.toString().toDouble(),
                i1shoppingCostText.text.toString().toDouble(),
                i1shoppingWhereText.text.toString(),
                i1shoppingWhenNeededDoneText.text.toString(),
                shoppingPriority,
                clientName!!
            )
        }
        // add chore item
        if (i1choresItemName.text.toString() != "") {
            Log.d(activityTAG, "buttonsOnClick: ${i1choresItemName.text}")
            val choreDifficulty = when (true) {
                i1choresDifficulty1.isChecked -> 1
                i1choresDifficulty2.isChecked -> 2
                i1choresDifficulty3.isChecked -> 3
                else -> 0
            }
            val chorePriority = when (true) {
                i1choresPriority1.isChecked -> 1
                i1choresPriority2.isChecked -> 2
                i1choresPriority3.isChecked -> 3
                else -> 0
            }
            addChoreItem(
                i1choresItemName.text.toString(),
                choreDifficulty,
                i1choresWhenNeededDoneText.text.toString(),
                chorePriority,
                clientName!!
            )
        }
        // go to home activity
        val gotoHomeScreen = Intent(this, TestHousemateActivity::class.java)
        startActivity(gotoHomeScreen)
    }
    // HELPER FUNCTIONS //
    // CLICK LISTENERS //
    private fun buttonsOnClick() {
        addItemButton.setOnClickListener {
            // -check if client name is saved in shared preferences.
            // -if it is add that as a property in the db item
            // ----
            // -if not true, add it to shared preferences
            // -then add that as a property in the db item
            var clientName = getNameOfClientFromSP()
            if (clientName == null) {
                Log.d(activityTAG, "buttonsOnClick: clientName pt1 = $clientName")
                val nameInputDialog = MaterialAlertDialogBuilder(this)
                val customAlertDialogView = LayoutInflater.from(this)
                    .inflate(R.layout.name_dialog_box, null, false)
                val inputNameDialog: EditText = customAlertDialogView.findViewById(R.id.input_name_dialog)
                nameInputDialog.setView(customAlertDialogView)
                    .setTitle("Type your name")
                    .setPositiveButton("Accept") { dialog, _ ->
                        clientName = inputNameDialog.text.toString()
                        sendNameOfClientToSP(clientName!!)
                        dialog.dismiss()
                        addItems(clientName)
                    }
                    .setNeutralButton("Anonymous") { dialog, _ ->
                        clientName = "anonymous"
                        sendNameOfClientToSP(clientName!!)
                        dialog.dismiss()
                        addItems(clientName)
                    }
                    .show()
                Log.d(activityTAG, "buttonsOnClick: prob doesn't happen concurrently.")
            } else {
                addItems(clientName)
            }
            Log.d(activityTAG, "buttonsOnClick: the rest happens anyways")
        }
    }
    // CLICK LISTENERS //
    // SET UP FUNCTIONS //
    private fun initiateVars() {
        sharedPref = this.getSharedPreferences(sharedPreferenceTag, Context.MODE_PRIVATE)
    }
    private fun bindUIWidgets() {
        addItemButton = findViewById(R.id.add_item_button)

        i1shoppingItemName = findViewById(R.id.item_add1Shopping_item_name)
        i1shoppingItemQty = findViewById(R.id.add1Shopping_item_qty)
        i1shoppingWhenNeededDoneText = findViewById(R.id.add1Shopping_when_needed_done_text)
        i1shoppingWhereText = findViewById(R.id.add1Shopping_where_text)
        i1shoppingCostText = findViewById(R.id.add1Shopping_cost_text)
        i1shoppingPriorityText = findViewById(R.id.add1Shopping_priority_text)
        i1shoppingPriority1 = findViewById(R.id.shopPriority_button_1)
        i1shoppingPriority2 = findViewById(R.id.shopPriority_button_2)
        i1shoppingPriority3 = findViewById(R.id.shopPriority_button_3)

        i1choresItemName = findViewById(R.id.add1Chores_item_name)
        i1choresWhenNeededDoneText = findViewById(R.id.add1Chores_when_needed_done_text)
        i1choresDifficulty = findViewById(R.id.add1Chores_difficulty)
        i1choresDifficulty1 = findViewById(R.id.chores_difficulty_button_1)
        i1choresDifficulty2 = findViewById(R.id.chores_difficulty_button_2)
        i1choresDifficulty3 = findViewById(R.id.chores_difficulty_button_3)
        i1choresPriorityText = findViewById(R.id.add1Chores_priority_text)
        i1choresPriority1 = findViewById(R.id.chores_priority_button_1)
        i1choresPriority2 = findViewById(R.id.chores_priority_button_2)
        i1choresPriority3 = findViewById(R.id.chores_priority_button_3)
    }
    // SET UP FUNCTIONS //
}