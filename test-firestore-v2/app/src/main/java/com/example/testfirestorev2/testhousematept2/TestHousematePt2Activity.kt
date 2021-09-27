package com.example.testfirestorev2.testhousematept2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.R

// use recyclerview, firebase, livedata, and coroutines

class TestHousematePt2Activity : AppCompatActivity() {

    private lateinit var shoppingBtn: Button
    private lateinit var choresBtn: Button
    private lateinit var shoppingRecyclerWidget: RecyclerView
    private lateinit var choresRecyclerWidget: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_housemate_pt2)
    }

    // SETUP FUNCTIONS //
    private fun bindUIWidgets() {
        shoppingBtn = findViewById(R.id.shopping_btn)
        choresBtn = findViewById(R.id.chores_btn)
        shoppingRecyclerWidget = findViewById(R.id.shopping_recycler_widget)
        choresRecyclerWidget = findViewById(R.id.chores_recycler_widget)
    }
    // SETUP FUNCTIONS //
}