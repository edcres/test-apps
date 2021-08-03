package com.example.testfirestorev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var testHousemateDb: Button
    private lateinit var fromDocsDbBtn: Button
    private lateinit var basicDbBtn: Button

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
//        fromDocsDbBtn.setOnClickListener {
//            val goToDocumDB = Intent(this, FromDocumentationActivity::class.java)
//            startActivity(goToDocumDB)
//        }
//        basicDbBtn.setOnClickListener {
//            val goToSimpleDB = Intent(this, BasicsActivity::class.java)
//            startActivity(goToSimpleDB)
//        }
    }

    // SETUP FUNCTIONS //
    private fun bindWidgetIDs() {
        testHousemateDb = findViewById(R.id.test_housemate_db)
        fromDocsDbBtn = findViewById(R.id.from_docs_db_btn)
        basicDbBtn = findViewById(R.id.basic_db_btn)
    }
}
