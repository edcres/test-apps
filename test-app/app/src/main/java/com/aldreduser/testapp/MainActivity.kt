package com.aldreduser.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aldreduser.testapp.addwidgettolayout.AddWidgetToLayoutActivity
import com.aldreduser.testapp.recyclerviewanddialog.RecyclerviewActivity

// https://www.youtube.com/watch?v=afl_i6uvvU0

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerviewActivityBtn: Button
    private lateinit var addLayoutWidgetsActivityBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bindUIWidgets()
        clickListeners()
    }

    private fun clickListeners() {
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
         recyclerviewActivityBtn = findViewById(R.id.recyclerview_activity_btn)
         addLayoutWidgetsActivityBtn = findViewById(R.id.add_layout_widgets_activity_btn)
    }
}