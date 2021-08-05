package com.example.testfirestorev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var testHousemateDb: Button
    private lateinit var testAllDb: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindWidgetIDs()
        buttonOnClick()
    }

    // CLICK LISTENERS //
    private fun buttonOnClick() {
        testHousemateDb.setOnClickListener {
            val goToTestHousemate = Intent(this, TestHousemateActivity::class.java)
            startActivity(goToTestHousemate)
        }
        testAllDb.setOnClickListener {
            val goToTestAll = Intent(this, MyOwnTestActivity::class.java)
            startActivity(goToTestAll)
        }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        testHousemateDb = findViewById(R.id.test_housemate_db)
        testAllDb = findViewById(R.id.test_all_db)
    }
}
