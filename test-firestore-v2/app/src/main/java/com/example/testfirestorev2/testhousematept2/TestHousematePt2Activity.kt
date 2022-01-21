package com.example.testfirestorev2.testhousematept2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testfirestorev2.R
import com.example.testfirestorev2.databinding.ActivityTestHousematePt2Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// use recyclerview, firebase, livedata, and coroutines

class TestHousematePt2Activity : AppCompatActivity() {

    private val TAG = "HousematePt2mTAG"

    private val mainSharedPrefTag = "TestHousemateActySP"
    private var binding: ActivityTestHousematePt2Binding? = null
    private lateinit var housemate2ViewModel: Housemate2ViewModel
    private val recyclerShoppingAdapter = HousemateShoppingRecyclerAdapter()
    private val recyclerChoresAdapter = HousemateChoresRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestHousematePt2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        housemate2ViewModel = ViewModelProvider(this)[Housemate2ViewModel::class.java]
        // shared pref for the viewModel
        housemate2ViewModel.sharedPref =
            this.getSharedPreferences(mainSharedPrefTag, Context.MODE_PRIVATE)
        binding?.apply {
            lifecycleOwner = this@TestHousematePt2Activity
            viewModel = housemate2ViewModel

            // Shopping adapter
            shoppingRecyclerWidget.adapter = recyclerShoppingAdapter
            shoppingRecyclerWidget.layoutManager =
                LinearLayoutManager(this@TestHousematePt2Activity)

            // Chores adapter
            choresRecyclerWidget.adapter = recyclerChoresAdapter
            choresRecyclerWidget.layoutManager =
                LinearLayoutManager(this@TestHousematePt2Activity)
        }
        Log.d(TAG, "onCreate: after binding")
        setUpObservers()
        clickListeners()
        startApplication()
    }

    private fun startApplication() {
        // set Up Database IDs And FetchData
        // try to get the groupId from shared preferences
        val currentClientGroupID = housemate2ViewModel.getCurrentGroupID()

        // if null, in a dialog box ask user what their group
        if (currentClientGroupID == null) {
            makeDialogBoxAndSetGroupID()
        } else {
            housemate2ViewModel.setShoppingItemsRealtime()
            housemate2ViewModel.setChoreItemsRealtime()
        }
    }

    //todo
    // check if group id in shared preferences
    // check if client id in shared preferences
    // check added by in shared preferences
    // then get data realtime

    // SETUP FUNCTIONS //
    private fun makeDialogBoxAndSetGroupID() {
        val inputDialog = MaterialAlertDialogBuilder(this)
        val customAlertDialogView = LayoutInflater.from(this)
            .inflate(R.layout.name_dialog_box, null, false)
        val inputNameDialog: EditText = customAlertDialogView.findViewById(R.id.input_name_dialog)
        inputDialog.setView(customAlertDialogView)
            .setTitle("Your group ID")
            .setPositiveButton("Accept") { dialog, _ ->
                housemate2ViewModel.clientGroupIDCollection = inputNameDialog.text.toString()
                Log.d(TAG, "makeDialogBoxAndSetGroupID: accept clicked " +
                            "${housemate2ViewModel.clientGroupIDCollection}")
                housemate2ViewModel.sendDataToSP(housemate2ViewModel.groupIdSPTag,
                    housemate2ViewModel.clientGroupIDCollection!!)

                housemate2ViewModel.setShoppingItemsRealtime()
                housemate2ViewModel.setChoreItemsRealtime()
                dialog.dismiss()
            }
            .setNegativeButton("New Group") { dialog, _ ->
                Log.d(TAG, "makeDialogBoxAndSetGroupID: negative button called")
                housemate2ViewModel.generateClientGroupID()
                dialog.dismiss()
            }
            .show()
    }

    private fun setUpObservers() {
        // observe these viewModel variables
        housemate2ViewModel.shoppingItems.observe(this, Observer { result ->
            // send the updates list to the ListAdapter
            // submitList() is how the adapter know how many items to display
            recyclerShoppingAdapter.submitList(result)
        })
        housemate2ViewModel.choreItems.observe(this, Observer { result ->
            recyclerChoresAdapter.submitList(result)
        })
    }
    // SETUP FUNCTIONS //

    // CLICK LISTENERS //
    private fun clickListeners() {
        binding!!.apply {

            clearSpBtn.setOnClickListener {
                housemate2ViewModel.clearSP()
            }

            shoppingBtn.setOnClickListener {
                shoppingRecyclerWidget.visibility = View.VISIBLE
                shoppingAddContainer.visibility = View.GONE
                choresRecyclerWidget.visibility = View.GONE
                choresAddContainer.visibility = View.GONE
            }
            choresBtn.setOnClickListener {
                shoppingRecyclerWidget.visibility = View.GONE
                shoppingAddContainer.visibility = View.GONE
                choresRecyclerWidget.visibility = View.VISIBLE
                choresAddContainer.visibility = View.GONE
            }

            addShoppingBtn.setOnClickListener {
                shoppingRecyclerWidget.visibility = View.GONE
                shoppingAddContainer.visibility = View.VISIBLE
                choresRecyclerWidget.visibility = View.GONE
                choresAddContainer.visibility = View.GONE
            }
            addChoresBtn.setOnClickListener {
                shoppingRecyclerWidget.visibility = View.GONE
                shoppingAddContainer.visibility = View.GONE
                choresRecyclerWidget.visibility = View.GONE
                choresAddContainer.visibility = View.VISIBLE
            }

            shoppingSendBtn.setOnClickListener {
                shoppingAddContainer.visibility = View.GONE
                shoppingRecyclerWidget.visibility = View.VISIBLE
                housemate2ViewModel.sendShoppingItemToDatabase(
                    shoppingEtName.text.toString(),
                    shoppingEtQty.text.toString().toDouble(),
                    shoppingEtCost.text.toString().toDouble(),
                    shoppingEtWhereToGetIt.text.toString(),
                    shoppingEtNeededBy.text.toString(),
                    shoppingEtPriority.text.toString().toInt(),
                    "Edd"
                )
            }
            choresSendBtn.setOnClickListener {
                choresAddContainer.visibility = View.GONE
                choresRecyclerWidget.visibility = View.VISIBLE
                housemate2ViewModel.sendChoresItemToDatabase(
                    choresEtName.text.toString(),
                    choresEtDifficulty.text.toString().toInt(),
                    choresEtNeededBy.text.toString(),
                    choresEtPriority.text.toString().toInt(),
                    "Edd"
                )
            }
        }
    }
    // CLICK LISTENERS //
}
