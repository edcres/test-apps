package com.aldreduser.testapp.basicrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlin.random.Random

// Explaining this activity:
// -recyclerview with a predefined list of data
// -the list is of object 'BasicRecyclerItem'
// -add and remove item features
// -the text of the main text is set here and the subtext is set in the adapter

class RecyclerviewActivity : AppCompatActivity() {

    private lateinit var insertItemBtn: Button
    private lateinit var removeItemBtn: Button
    private lateinit var recyclerView: RecyclerView
    private val exampleList = fillUpRecyclerView(100)
    private val recyclerAdapter = TestRecyclerviewAdapter(exampleList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        setUIWidgets()

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        eventListeners()
    }

    // CLICK LISTENERS //
    private fun eventListeners() {
        insertItemBtn.setOnClickListener {
            insertItem()
        }
        removeItemBtn.setOnClickListener {
            removeItem()
        }
    }
    // CLICK LISTENERS //

    // HELPER FUNCTIONS //
    private fun fillUpRecyclerView(size: Int): MutableList<BasicRecyclerItem> {

        val itemsList = mutableListOf<BasicRecyclerItem>()

        for (i in 1..size) {
            // todo set the subtext in the adapter
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext")    //position in mutableList()
            itemsList.add(thisItem)
        }

        return itemsList
    }

    private fun insertItem() {
        // add an item at a random position
        val index: Int = Random.nextInt(6)  // from 0 to 5
        val itemNumber = exampleList.size + 1

        val newItem = BasicRecyclerItem(
            "Item #$itemNumber",
            "Subtext"
        )

        exampleList.add(index, newItem)
        // update the recyclerview with the new item
        // recyclerAdapter.notifyDataSetChanged()   // be more specific than this
        recyclerAdapter.notifyItemInserted(index)
    }

    private fun removeItem() {
        val index = Random.nextInt(6)  // from 0 to 5

        exampleList.removeAt(index)
        recyclerAdapter.notifyItemRemoved(index)
    }
    // HELPER FUNCTIONS //

    // SET UP FUNCTIONS //
    private fun setUIWidgets() {
        recyclerView = findViewById(R.id.basic_recyclerview)
        insertItemBtn = findViewById(R.id.insert_item_btn)
        removeItemBtn = findViewById(R.id.remove_item_btn)
    }
}
