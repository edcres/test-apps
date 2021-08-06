package com.example.testfirestorev2

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddItemActivity : AppCompatActivity() {

    private val homeActivity = TestHousemateActivity
    private val db = Firebase.firestore
    private lateinit var addItemButton: Button
    private val TAG = "AddItemActivityTAG"

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

        bindUIWidgets()
        buttonsOnClick()
    }

    // todo: create an add item activity and put these functions there
    // HELPER FUNCTIONS //
    private fun addShoppingItem(
        itemName: String,
        itemQuantity: Double,
        itemCost: Double,
        purchaseLocation: String,
        neededBy: String,   // try and make this a date
        itemPriority: Int
    ) {

        val shoppingItemData = hashMapOf(
            homeActivity.NAME_FIELD to itemName,
            homeActivity.QUANTITY_FIELD to itemQuantity,
            homeActivity.COST_FIELD to itemCost,
            homeActivity.PURCHASE_LOCATION_FIELD to purchaseLocation,
            homeActivity.NEEDED_BY_FIELD to neededBy,
            homeActivity.PRIORITY_FIELD to itemPriority
        )

        // access the clientGroup, then the client, then the shopping item
        db.collection(TestHousemateActivity.GENERAL_COLLECTION).document(TestHousemateActivity.GROUPS_DOC)
            .collection(homeActivity.clientGroupIDCollection).document(TestHousemateActivity.CLIENTS_DOC)
            .collection(homeActivity.clientIDCollection).document(TestHousemateActivity.SHOPPING_LIST)
            .collection(TestHousemateActivity.SHOPPING_ITEMS_COLLECTION).document(itemName)
            .set(shoppingItemData)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
    private fun addChoreItem(
        itemName: String,
        difficulty: Int,
        neededBy: String,   // try and make this a date
        itemPriority: Int
    ) {
        val choresItemData = hashMapOf(
            TestHousemateActivity.NAME_FIELD to itemName,
            TestHousemateActivity.DIFFICULTY_FIELD to difficulty,
            TestHousemateActivity.NEEDED_BY_FIELD to neededBy,
            TestHousemateActivity.PRIORITY_FIELD to itemPriority
        )

        // access the clientGroup, then the client, then the shopping item
        db.collection(TestHousemateActivity.GENERAL_COLLECTION).document(TestHousemateActivity.GROUPS_DOC)
            .collection(homeActivity.clientGroupIDCollection).document(TestHousemateActivity.CLIENTS_DOC)
            .collection(homeActivity.clientIDCollection).document(TestHousemateActivity.CHORES_LIST)
            .collection(TestHousemateActivity.CHORE_ITEMS_COLLECTION).document(itemName)
            .set(choresItemData)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    // CLICK LISTENERS //
    private fun buttonsOnClick() {
        addItemButton.setOnClickListener {
            // add item data to the database
            // check if the name is filled for each. Only send info if it's filled.
            if (i1shoppingItemName.text != null) {
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
                    shoppingPriority
                )
            }

            if (i1choresItemName.text != null) {
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
                    chorePriority
                )
            }
        }
    }

    // SET UP FUNCTIONS //
    private fun bindUIWidgets() {
        addItemButton = findViewById(R.id.add_item_button)

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
}