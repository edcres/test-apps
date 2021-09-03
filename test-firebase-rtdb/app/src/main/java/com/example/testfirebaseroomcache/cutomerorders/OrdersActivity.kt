package com.example.testfirebaseroomcache.cutomerorders

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testfirebaseroomcache.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// save the store# in shared preferences
// save date added
// save order# ( "$storeNumber" + "<5moreNumbersInOrder>" )
// click on order to retrieve it
//  -delete option
// preview displays: order# || short note about the order || date added


// have a list of order numbers inside the locations node
// db.child(ORDERS_DB_NODE).child(LOCATION_NODE).child(locationID!!).child(orderNumber)

// todo: setup some realtime fetch (the list of past orders)

class OrdersActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private val db = Firebase.database.reference

    private lateinit var customerOrders: MutableList<CustomerOrder>

    private lateinit var orderNumTxt: TextView
    private lateinit var dateAddedTxt: TextView
    private lateinit var orderDetailsEdtTxt: EditText
    private lateinit var orderNotesEdtTxt: EditText
    private lateinit var saveOrderBtn: Button
    private lateinit var removeOrderBtn: Button
    private lateinit var pastOrdersLinearLayt: LinearLayout
    private lateinit var removeLocationBtn: Button

    companion object {
        private const val TAG = "OrdersActyTag"
        private const val LOCATION_ID_SP_TAG = "Location id"
        // orders db -> Location -> h6872 -> orders ()
        private const val ORDERS_DB_NODE = "Orders"
        private const val LOCATION_NODE = "Location"
        private var locationID: String? = null
//        private const val ORDERS_NODE = "Orders"
        private var orderNumber = ""
        private const val ORDER_NUMBER_FIELD = "Number"
        private const val DETAILS_FIELD = "Details"
        private const val NOTE_FIELD = "Note"
        private const val DATE_FIELD = "date added"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        sharedPref = this.getSharedPreferences("OrdersActivitySP", Context.MODE_PRIVATE)
        bindUIWidgets()
        //get location id
        getDataFromSPAndSetUpUI()
        clickListeners()
    }

    // HELPER FUNCTIONS //
    private fun doAfterIdFetch() {
        // todo:
        //  -get past orders list from db
        //  -populate the past_orders_linear_layout
        db.child(ORDERS_DB_NODE).child(LOCATION_NODE).child(locationID!!).child(orderNumber)
            .get().addOnSuccessListener {
                //todo:
                // for each customer id {
                customerOrders.add()
                // add a widget with the order info to a list of these widgets
                // }
                // add the list of customer order widgets inside the past_orders_linear_layout
                val textForBtn = "${removeLocationBtn.text} $locationID"
                removeLocationBtn.text = textForBtn
            }
            .addOnFailureListener {
                Log.d(TAG, "doAfterIdFetch: Database fetch failed")
            }
    }
    private fun getDataFromSPAndSetUpUI() {
        // try to get from SP, if null, pop a dialog box
        locationID = sharedPref.getString(LOCATION_ID_SP_TAG, null)

        if (locationID == null) {
            // pop a dialog box and have user type the location id
            makeDialogBoxAndSetLocationID()
        } else {
            doAfterIdFetch() // get data from db, populate UI, display store ID
        }
    }
    @SuppressLint("ApplySharedPref")
    private fun sendDataToSP(theLocationId: String, sPTag: String) {
        // save location id tp SP
        val spEditor: SharedPreferences.Editor = sharedPref.edit()
        spEditor.putString(sPTag, theLocationId).commit()
    }
    @SuppressLint("ApplySharedPref")
    private fun clearSP() {
        // removes the store number from SP
        sharedPref.edit().clear().commit()
    }
    private fun makeDialogBoxAndSetLocationID() {
        val stringInputDialog = MaterialAlertDialogBuilder(this)
        val customAlertDialogView = LayoutInflater.from(this)
            .inflate(R.layout.string_dialog_box, null, false)
        val inputStringDialog:EditText= customAlertDialogView.findViewById(R.id.string_input_dialog)
        inputStringDialog.hint = "H7428"
        stringInputDialog.setView(customAlertDialogView)
            .setTitle("Store number")
            .setPositiveButton("Accept") { dialog,  _ ->
                locationID = inputStringDialog.text.toString()
                Log.d(TAG, "makeDialogBoxAndSetLocationID: accept clicked $locationID")
                sendDataToSP(locationID!!, LOCATION_ID_SP_TAG)
                doAfterIdFetch()    // get data from db, populate UI, display store ID
                dialog.dismiss()
            }
            .show()
    }
    private fun breakDateFormat(dateString: String): List<Int> {
        var monthDayYear: List<Int>
        dateString.split("/").let { (month, day, year) ->
            monthDayYear = listOf(month.toInt(), day.toInt(), year.toInt())
        }
        // returns an int array of (month, day, year)
        return monthDayYear
    }
    // HELPER FUNCTIONS //

    // SETUP FUNCTIONS //
    private fun bindUIWidgets() {
        orderNumTxt = findViewById(R.id.order_num_txt)
        dateAddedTxt = findViewById(R.id.date_added)
        orderDetailsEdtTxt = findViewById(R.id.order_details_edt_txt)
        orderNotesEdtTxt = findViewById(R.id.order_notes_edt_Txt)
        saveOrderBtn = findViewById(R.id.save_order_btn)
        removeOrderBtn = findViewById(R.id.remove_order_btn)
        pastOrdersLinearLayt = findViewById(R.id.past_orders_linear_layout)
        removeLocationBtn = findViewById(R.id.remove_location_btn)
    }
    @SuppressLint("SetTextI18n")
    private fun clickListeners() {
        saveOrderBtn.setOnClickListener {
            // Check if it already has one date, if not add a new one)
            val dateAdded: String = if(dateAddedTxt.toString() == "") {
                val timeInstance = java.util.Calendar.getInstance()
                "${timeInstance.get(java.util.Calendar.MONTH) + 1}/" +
                        "${timeInstance.get(java.util.Calendar.DAY_OF_MONTH)}/" +
                        "${timeInstance.get(java.util.Calendar.YEAR)}"
            } else {
                dateAddedTxt.toString()
            }
            val customerOrder = CustomerOrder(
                orderNumTxt.text.toString(),
                orderDetailsEdtTxt.text.toString(),
                orderNotesEdtTxt.text.toString(),
                dateAdded
            )

            // todo: send it to db
            // todo: get/generate order number
            // Check if the 'order_num_txt' is empty, if yes generate a new order number.
            //  If not, use 'order_num_txt.text.toString' to update that order# nose
            if (orderNumTxt.text == "") {
                // make a new order#
                // check the last order in the database
                // get the last 5 digits, add one and make it in the same format (H6872-74376)
                // orderNumber =
            } else {
                orderNumber = orderNumTxt.text.toString()
            }
            db.child(ORDERS_DB_NODE).child(LOCATION_NODE).child(locationID!!).child(orderNumber)
                .setValue(customerOrder)
        }
        removeOrderBtn.setOnClickListener {
            orderNumber = orderNumTxt.text.toString()
            if(orderNumber != "") {
                db.child(ORDERS_DB_NODE).child(LOCATION_NODE).child(locationID!!)
                    .child(orderNumber).removeValue()
                    .addOnSuccessListener {
                        // clear texts
                        orderNumTxt.text = ""
                        orderDetailsEdtTxt.setText("")
                        orderNotesEdtTxt.setText("")
                        dateAddedTxt.text = ""
                    }
            }
        }
        removeLocationBtn.setOnClickListener {
            clearSP()
            removeLocationBtn.text = "Remove Location"
        }
    }
    // SETUP FUNCTIONS //
}
