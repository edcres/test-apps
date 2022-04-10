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
            val intent = Intent(this, ActToActActivity::class.java).also {
                it.putExtra(ACT_TO_ACT_INTENT_TAG, message)
            }
//            intent.putExtra(ACT_TO_ACT_INTENT_TAG, message)
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