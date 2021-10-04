package com.example.testfirestorev2.testhousematept2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.R
import com.example.testfirestorev2.databinding.ActivityMainBinding
import com.example.testfirestorev2.databinding.ActivityTestHousematePt2Binding

// use recyclerview, firebase, livedata, and coroutines

class TestHousematePt2Activity : AppCompatActivity() {

    private val TAG = "HousematePt2 mTAG"
    private var binding: ActivityTestHousematePt2Binding? = null
    private lateinit var housemate2ViewModel: Housemate2ViewModel

    private lateinit var shoppingBtn: Button
    private lateinit var choresBtn: Button
    private lateinit var shoppingRecyclerWidget: RecyclerView
    private lateinit var choresRecyclerWidget: RecyclerView
    private val recyclerAdapter = HousemateRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_housemate_pt2)
        binding = ActivityTestHousematePt2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        Log.d(TAG, "onCreate: before 'setUpViewModel()'")
        setUpViewModel()    // viewModel init{} is called here
        Log.d(TAG, "onCreate: after 'setUpViewModel()'")
        binding?.apply {
            lifecycleOwner = this@TestHousematePt2Activity
            viewModel = housemate2ViewModel
            shoppingRecyclerWidget.adapter = recyclerAdapter
            shoppingRecyclerWidget.layoutManager = LinearLayoutManager(this@TestHousematePt2Activity)
        }
        Log.d(TAG, "onCreate: after binding")
        setUpObservers()
    }


//    // just a test function
//    // todo: delete this later
//    var textAlreadyUpdated = false
//    private fun displayTextText(itemsList: MutableList<ShoppingItem>) {
//
//        if (!textAlreadyUpdated) {
//            var iterator = 0
//            for (item in itemsList) {
//                iterator++
//                val lineToDisplay = "${binding!!.testItemList.text}$iterator -> ${item.name}\n"
//                binding!!.testItemList.text = lineToDisplay
//                textAlreadyUpdated = true
//            }
//        } else {
//            var iterator = 0
//            binding!!.testItemList.text = ""
//            for (item in itemsList) {
//                iterator++
//                val lineToDisplay = "${binding!!.testItemList.text}$iterator -> ${item.name}\n"
//                binding!!.testItemList.text = lineToDisplay
//            }
//        }
//    }

    // SETUP FUNCTIONS //
    private fun setUpObservers() {
        // owner 'this' might be a bug.
        housemate2ViewModel.shoppingItems.observe(this, Observer { result ->
            // send the updates list to the ListAdapter
            // submitList() is how the adapter know how many items to display
            recyclerAdapter.submitList(result)

            Log.d("HsMtTest2TAG", result[0].name.toString())
            Log.d("HsMtTest2TAG", result[0].neededBy.toString())
        })
    }

    private fun setUpViewModel() {
        housemate2ViewModel = ViewModelProvider(this)[Housemate2ViewModel::class.java]
    }
    // SETUP FUNCTIONS //

}
