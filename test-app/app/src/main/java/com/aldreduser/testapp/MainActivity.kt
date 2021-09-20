package com.aldreduser.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aldreduser.testapp.addwidgettolayout.AddWidgetToLayoutActivity
import com.aldreduser.testapp.basicrecyclerview.RecyclerviewActivity
import com.aldreduser.testapp.recyclerviewclicklistener.RecyclerClickListener
import com.aldreduser.testapp.recyclerwidgetclick.RecyclerWidgetClickActivity

// https://www.youtube.com/watch?v=afl_i6uvvU0

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerWidgetClickBtn: Button
    private lateinit var recyclerClickActivityBtn: Button
    private lateinit var recyclerviewActivityBtn: Button
    private lateinit var addLayoutWidgetsActivityBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUIWidgets()
        clickListeners()
    }

    private fun clickListeners() {
        recyclerWidgetClickBtn.setOnClickListener {
            val goToActivity = Intent(this, RecyclerWidgetClickActivity::class.java)
            startActivity(goToActivity)
        }
        recyclerClickActivityBtn.setOnClickListener {
            val goToActivity = Intent(this, RecyclerClickListener::class.java)
            startActivity(goToActivity)
        }
        recyclerviewActivityBtn.setOnClickListener {
            val goToActivity = Intent(this, RecyclerviewActivity::class.java)
            startActivity(goToActivity)
        }
        addLayoutWidgetsActivityBtn.setOnClickListener {
            val goToActivity = Intent(this, AddWidgetToLayoutActivity::class.java)
            startActivity(goToActivity)
        }
    }

    private fun bindUIWidgets() {
        recyclerWidgetClickBtn = findViewById(R.id.recycler_widget_click_btn)
        recyclerClickActivityBtn = findViewById(R.id.recycler_click_activity_btn)
        recyclerviewActivityBtn = findViewById(R.id.recyclerview_activity_btn)
        addLayoutWidgetsActivityBtn = findViewById(R.id.add_layout_widgets_activity_btn)
    }
}