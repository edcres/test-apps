package com.example.testfirebaseroomcache.cutomerorders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testfirebaseroomcache.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// save the store# in shared preferences
// save date added
// save order# ( "$storeNumber" + "<5moreNumbersInOrder>" )
// click on order to retrieve it
//  -delete option
// preview displays: order# || short note about the order || date added

class OrdersActivity : AppCompatActivity() {

    private val db = Firebase.database.reference

    private lateinit var orderNumTxt: TextView
    private lateinit var dateAddedTxt: TextView
    private lateinit var orderDetailsEdtTxt: EditText
    private lateinit var orderNotesEdtTxt: EditText
    private lateinit var saveOrderBtn: Button
    private lateinit var removeOrderBtn: Button
    private lateinit var pastOrdersLinearLayt: LinearLayout
    private lateinit var removeLocationBtn: Button

    companion object {
        // orders db -> location(h6872) -> orders ()
        private const val ORDERS_DB_NODE = "Orders Node"
        private const val LOCATION_NODE = "Location"
        private const val ORDERS_NODE = "Orders"
        private const val ORDER_NUMBER_FIELD = "Number"
        private const val DETAILS_FIELD = "Details"
        private const val NOTE_FIELD = "Note"
        private const val DATE_FIELD = "date added"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        bindUIWidgets()
        clickListeners()
    }

    // HELPER FUNCTIONS //
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

        }
        removeOrderBtn.setOnClickListener {
            // todo: remove that order from the db
            orderNumTxt.text.toString()
        }
        removeLocationBtn.setOnClickListener {
            // clear shared preferences (the store number <ie. H6872>)
        }
    }
    // SETUP FUNCTIONS //
}
