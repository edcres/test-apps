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

class RecyclerviewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        val exampleList = fillUpRecyclerView(100)
        recyclerView = findViewById(R.id.basic_recyclerview)
        recyclerView.adapter = TestRecyclerviewAdapter(exampleList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fillUpRecyclerView(size: Int): List<BasicRecyclerItem> {

        val itemsList = mutableListOf<BasicRecyclerItem>()

        for (i in 1..size) {
            // todo set the subtext in the adapter
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext")    //position in mutableList()
            itemsList.add(thisItem)
        }

        return itemsList
    }
}