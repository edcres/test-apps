package com.aldreduser.testapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldreduser.testapp.R
import com.aldreduser.testapp.ui.adapters.TestRecyclerviewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

// https://www.youtube.com/watch?v=afl_i6uvvU0

class MainActivity : AppCompatActivity() {

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View
    private lateinit var nameTextField: EditText
    private lateinit var testDialogBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        testDialogBtn = findViewById(R.id.test_dialog_btn)

        // Setting onClickListener to Floating action button
        testDialogBtn.setOnClickListener(View.OnClickListener {
            // Inflate Custom alert dialog view
            customAlertDialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_view, null, false)

            launchCustomAlertDialog()
        })
    }

    private fun launchCustomAlertDialog() {
        nameTextField = customAlertDialogView.findViewById(R.id.person_name_text)

        var clientName = ""
        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setPositiveButton("Accept") { dialog, _ ->
                clientName = nameTextField.text.toString()
                Log.d("TAG", "launchCustomAlertDialog: 1111 clientName = $clientName")
                dialog.dismiss()
            }
            .setNeutralButton("Anonymous") { dialog, _ ->
                clientName = "Anonymous"
                dialog.dismiss()
            }
            .show()
        Log.d("TAG", "launchCustomAlertDialog: hjjnj clientName = $clientName")
    }

//    private fun setupUpAppBar() {
//        mainActivityTopAppBar.title = "Workouts"
//    }
//
//    private fun populateRecyclerView() {
//        workoutsRecyclerView.layoutManager = LinearLayoutManager(this)
//        workoutsRecyclerView.adapter = TestRecyclerviewAdapter()
//    }
}