package com.example.testfirestorev2.testhousematept2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testfirestorev2.databinding.ActivityTestHousematePt2Binding

// use recyclerview, firebase, livedata, and coroutines

class TestHousematePt2Activity : AppCompatActivity() {

    private val TAG = "HousematePt2 mTAG"
    private var binding: ActivityTestHousematePt2Binding? = null
    private lateinit var housemate2ViewModel: Housemate2ViewModel
    private val recyclerShoppingAdapter = HousemateShoppingRecyclerAdapter()
    private val recyclerChoresAdapter = HousemateChoresRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestHousematePt2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpViewModel()    // viewModel init{} is called here
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
    }

    // CLICK LISTENERS //
    private fun clickListeners() {
        binding!!.apply {

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

    // SETUP FUNCTIONS //
    private fun setUpObservers() {
        // owner 'this' might be a bug.
        housemate2ViewModel.shoppingItems.observe(this, Observer { result ->
            // send the updates list to the ListAdapter
            // submitList() is how the adapter know how many items to display
            recyclerShoppingAdapter.submitList(result)

//            Log.d("HsMtTest2TAG", result[0].name.toString())    creates a bug if list the is empty
//            Log.d("HsMtTest2TAG", result[0].neededBy.toString())
        })
        housemate2ViewModel.choreItems.observe(this, Observer { result ->
            recyclerChoresAdapter.submitList(result)
        })
    }

    private fun setUpViewModel() {
        housemate2ViewModel = ViewModelProvider(this)[Housemate2ViewModel::class.java]
    }
    // SETUP FUNCTIONS //
}
