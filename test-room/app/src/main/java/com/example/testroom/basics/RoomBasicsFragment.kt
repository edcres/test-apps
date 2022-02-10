package com.example.testroom.basics

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testroom.R
import com.example.testroom.basics.data.WorkoutBasics
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * info:
 * - By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the
 *      main thread.
 */

// todo: check if the id is autoIncrementing

class RoomBasicsFragment : Fragment() {

    private val fragmentTAG = "BasicFragTAG"
    private lateinit var viewModelFactory: WorkoutBasicsViewModelFactory
    private lateinit var viewModel: WorkoutBasicsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: BasicsRecyclerAdapter
    private lateinit var addItemFab: FloatingActionButton
    private lateinit var deleteAllBtn: Button
    private lateinit var addItemContainer: LinearLayout
    private lateinit var workoutEdtTxt: EditText
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_basics, container, false)
        setUpRecyclerView(view)
        setUpWidgets(view)

        viewModelFactory = WorkoutBasicsViewModelFactory(requireActivity().application)
        viewModel= ViewModelProvider(this, viewModelFactory)[WorkoutBasicsViewModel::class.java]

        addItemFab.setOnClickListener {
            workoutEdtTxt.setText("")
            hideWidgets()
        }
        deleteAllBtn.setOnClickListener {
            viewModel.deleteAllWorkouts()
        }
        saveBtn.setOnClickListener {
            val workoutName = workoutEdtTxt.text.toString()
            viewModel.insert(WorkoutBasics(name = workoutName))
            hideWidgets()
        }
        viewModel.allWorkouts.observe(viewLifecycleOwner) { workouts ->
            Log.d(fragmentTAG, "workouts: ${viewModel.allWorkouts.value}")
            Log.d(fragmentTAG, "workouts size: ${viewModel.allWorkouts.value?.size}")
            Log.d(fragmentTAG, "local workouts: $workouts")
            Log.d(fragmentTAG, "local workouts size: ${workouts.size}")
            recyclerAdapter.submitList(workouts)
        }
        return view
    }

    // SETUP //
    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = BasicsRecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
    }
    private fun setUpWidgets(view: View) {
        addItemFab = view.findViewById(R.id.add_item_fab)
        deleteAllBtn = view.findViewById(R.id.delete_all_btn)
        addItemContainer = view.findViewById(R.id.add_item_container)
        workoutEdtTxt = view.findViewById(R.id.workout_edt_txt)
        saveBtn = view.findViewById(R.id.save_btn)
    }
    // SETUP //

    // HELPER //
    private fun hideWidgets() {
        if(addItemContainer.visibility == View.GONE) {
            recyclerView.visibility = View.GONE
            addItemFab.visibility = View.GONE
            deleteAllBtn.visibility = View.GONE
            addItemContainer.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            addItemFab.visibility = View.VISIBLE
            deleteAllBtn.visibility = View.VISIBLE
            addItemContainer.visibility = View.GONE
        }
    }
}