package com.aldreduser.testapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldreduser.testapp.R
import com.aldreduser.testapp.ui.adapters.TestRecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_main.*

// https://www.youtube.com/watch?v=afl_i6uvvU0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUpAppBar()
        populateRecyclerView()
    }

    private fun setupUpAppBar() {
        mainActivityTopAppBar.title = "Workouts"
    }

    private fun populateRecyclerView() {
        workoutsRecyclerView.layoutManager = LinearLayoutManager(this)
        workoutsRecyclerView.adapter = TestRecyclerviewAdapter()
    }
}