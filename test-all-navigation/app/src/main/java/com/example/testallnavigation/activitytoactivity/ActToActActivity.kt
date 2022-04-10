package com.example.testallnavigation.activitytoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.testallnavigation.ACT_TO_ACT_INTENT_TAG
import com.example.testallnavigation.R

// todo: send a bundle with an intent

/**
 * This activity is the receiver from the same activity
 *
 * Intent and Bundle
 *  - I think you can send a Bundle using an Intent
 *
 *  -A Bundle is an object that stores key-value pairs. Android uses a Bundle to restore
 *      the full state of an activity.
 *      - Good practice is to avoid storing custom objects into a Bundle and save instead
 *      only the information needed to save an object's state.
 */

class ActToActActivity : AppCompatActivity() {

    private lateinit var actNameTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_to_act)

        actNameTxt = findViewById(R.id.act_name_txt)

//        val receivedMsg = intent.getBundleExtra(ACT_TO_ACT_INTENT_TAG)
        val receivedMsg = intent.getStringExtra(ACT_TO_ACT_INTENT_TAG)

        actNameTxt.text = receivedMsg
    }
}