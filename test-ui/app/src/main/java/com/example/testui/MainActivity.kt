package com.example.testui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var toNavCompBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toNavCompBtn = findViewById(R.id.to_nav_comp_btn)

        toNavCompBtn.setOnClickListener {
            val intent = Intent(this, NavCompActivity::class.java)
            startActivity(intent)
        }
    }
}