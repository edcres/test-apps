package com.example.testallnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testallnavigation.activitytoactivity.ActToActActivity
import com.example.testallnavigation.fragmentsnavcomnponent.FragNavCompActivity
import com.example.testallnavigation.fragmenttofragment.FragToFragActivity

class MainActivity : AppCompatActivity() {

    private lateinit var actToActBtn: Button
    private lateinit var fragToFragBtn: Button
    private lateinit var fragNavComponentBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actToActBtn = findViewById(R.id.act_to_act_btn)
        fragToFragBtn = findViewById(R.id.frag_to_frag_btn)
        fragNavComponentBtn = findViewById(R.id.frag_nav_component_btn)

        actToActBtn.setOnClickListener {
            val message = "Intent Receiver Activity"
            val messagePosition = 1
            val intentBundle = Bundle()
            intentBundle.putString(ACT_TO_ACT_MESSAGE_BUNDLE_KEY, message)
            intentBundle.putInt(ACT_TO_ACT_POSITION_BUNDLE_KEY, messagePosition)
            val intent = Intent(this, ActToActActivity::class.java)
            // Don't have to declare a bundle explicitly, can just add more put extras to the 'intent' and acts as a bundle
//            intent.putExtra(ACT_TO_ACT_MESSAGE_BUNDLE_KEY, message)
//            intent.putExtra(ACT_TO_ACT_POSITION_BUNDLE_KEY, message)
            intent.putExtras(intentBundle)
            startActivity(intent)
        }
        fragToFragBtn.setOnClickListener {
            val intent = Intent(this, FragToFragActivity::class.java)
            startActivity(intent)
        }
        fragToFragBtn.setOnClickListener {
            val intent = Intent(this, FragNavCompActivity::class.java)
            startActivity(intent)
        }
    }
}