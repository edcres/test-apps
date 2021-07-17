package com.example.testfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOnClick()
    }

    private fun buttonOnClick() {
        simple_db_btn.setOnClickListener {
            val goToSimpleDB = Intent(this, BasicsActivity::class.java)
            startActivity(goToSimpleDB)
        }
    }
}