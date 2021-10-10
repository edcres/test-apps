package com.example.testcoroutines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

// WARNING: I gave up on this and concluded it's better to just call functions from inside the dialog.

// User presses a button that pops up a dialog that asks to input a name
//  the name is then displayed in a TextView
// A function to update the TextView with the name awaits until the user inputs the name and accepts

class DialogAwaitFragment : Fragment() {

    private lateinit var displayNameTxt: TextView
    private lateinit var inputNameBtn: Button
    private lateinit var nameToDisplay: Deferred<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dialog_await, container, false)
        bindUIWidgets(view)
        clickListeners()
        return view
    }

    private fun makeDialogBoxAndSetGroupID() {

//            var nameToInput: String



            val nameInputDialog = MaterialAlertDialogBuilder(requireContext())
            val customAlertDialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialod_input_et, null, false)
            val inputNameDialog: EditText =
                customAlertDialogView.findViewById(R.id.input_dialog_et)
            nameInputDialog.setView(customAlertDialogView)
                .setTitle("Type the name")
                .setPositiveButton("Accept") { dialog, _ ->
                    val nameToInput = inputNameDialog.text.toString()
//                        nameToInput = inputNameDialog.text.toString()
                    if (nameToInput == "") {
                        Toast.makeText(requireContext(), "Type a name", Toast.LENGTH_SHORT)
                            .show()
                        makeDialogBoxAndSetGroupID()
                    } else if (nameToInput != "") {
//                        displayTheName(nameToInput)
                        GlobalScope.launch() {
                            nameToDisplay = async {
                                nameToInput
                            }
                        }
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.show()





//        return nameToDisplay

    }

    private fun displayTheName(nameToDisplay: Deferred<String>) {
        GlobalScope.launch() {
            displayNameTxt.text = nameToDisplay.await()
        }
    }

    private fun clickListeners() {
        inputNameBtn.setOnClickListener {
            makeDialogBoxAndSetGroupID()
//            if (nameToDisplay != null) {

                    displayTheName(nameToDisplay)

//            }
        }
    }

    private fun bindUIWidgets(view: View) {
        displayNameTxt = view.findViewById(R.id.display_name_txt)
        inputNameBtn = view.findViewById(R.id.input_name_btn)
    }
}