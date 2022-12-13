package com.aldreduser.testapp.dateclass

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.aldreduser.testapp.R
import java.text.DateFormat
import java.util.*

private const val TAG = "date_view__TAG"

class DateActivity : AppCompatActivity() {

    private lateinit var firstTxt: TextView
    private lateinit var firstBtn: Button
    private lateinit var secondTxt: TextView
    private lateinit var secondBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        bindUIWidgets()

        dateFormat()
    }

    private fun dateFormat() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val currentDate = DateFormat.getDateInstance().format(calendar.time)

        Log.d(TAG, "calendar: ${calendar.time}\n.")     // Mon May 16 16:34:52 EDT 2022

        Log.d(TAG, "dataFormat: ${currentDate}")

        firstTxt.text = dayOfMonth.toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            secondTxt.text = calendar.calendarType.toString()
        }
    }

    private fun bindUIWidgets() {
        firstTxt = findViewById(R.id.first_txt)
        firstBtn = findViewById(R.id.first_btn)
        secondTxt = findViewById(R.id.second_txt)
        secondBtn = findViewById(R.id.second_btn)
    }
}