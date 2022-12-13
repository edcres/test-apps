package com.aldreduser.testapp.recyclerwidgetclick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R
import com.aldreduser.testapp.basicrecyclerview.BasicRecyclerItem
import com.aldreduser.testapp.recyclerviewclicklistener.RecyclerClickListenerAdapter

// User clicks on the big text in a recycler item and the small text changes

class RecyclerWidgetClickActivity
    : AppCompatActivity(), RecyclerWidgetAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private val exampleList = fillUpRecyclerView(100)
    private val recyclerAdapter = RecyclerWidgetAdapter(exampleList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_widget_click)

        setUIWidgets()

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item position: $position clicked", Toast.LENGTH_SHORT)
            .show()
        val clickedItem = exampleList[position]
        clickedItem.text1 = "Clicked"
        recyclerAdapter.notifyItemChanged(position)
    }

    // HELPER FUNCTIONS //
    private fun fillUpRecyclerView(size: Int): MutableList<BasicRecyclerItem> {

        val itemsList = mutableListOf<BasicRecyclerItem>()

        for (i in 1..size) {
            // todo set the subtext in the adapter
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext")  //position in mutableList()
            itemsList.add(thisItem)
        }

        return itemsList
    }

    // SET UP FUNCTIONS //
    private fun setUIWidgets() {
        recyclerView = findViewById(R.id.widget_recyclerview)
    }
}