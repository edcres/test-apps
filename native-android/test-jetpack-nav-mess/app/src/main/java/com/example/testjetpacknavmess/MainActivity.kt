package com.example.testjetpacknavmess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("mainAct__TAG", "onCreate: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("mainAct__TAG", "onDestroy: called")
    }
}