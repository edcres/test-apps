package com.example.testfirestorev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testfirestorev2.docsandnotes.MyOwnTestActivity
import com.example.testfirestorev2.testhousemate.TestHousemateActivity
import com.example.testfirestorev2.testhousematept2.TestHousematePt2Activity
import com.example.testfirestorev2.withrecyclerview.WithRecyclerviewActivity

class MainActivity : AppCompatActivity() {

    private lateinit var testHousemateDb: Button
    private lateinit var testAllDb: Button
    private lateinit var withRecyclerview: Button
    private lateinit var testHousematePt2Db: Button

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
        withRecyclerview.setOnClickListener {
            val goToRecyclerview = Intent(this, WithRecyclerviewActivity::class.java)
            startActivity(goToRecyclerview)
        }
        withRecyclerview.setOnClickListener {
            val goToTestHousematePt2 = Intent(this, TestHousematePt2Activity::class.java)
            startActivity(goToTestHousematePt2)
        }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        testHousemateDb = findViewById(R.id.test_housemate_db)
        testAllDb = findViewById(R.id.test_all_db)
        withRecyclerview = findViewById(R.id.with_recyclerview)
        testHousematePt2Db = findViewById(R.id.test_housemate_pt2_db)
    }
}
