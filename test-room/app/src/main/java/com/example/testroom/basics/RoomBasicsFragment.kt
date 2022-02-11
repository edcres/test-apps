package com.example.testroom.basics

import android.os.Bundle
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

// todo: delete db data and add more
// todo: add a position attribute to the Entity
//  - default position is at the bottom. So list size (add item to vm list first)

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
        viewModelFactory = WorkoutBasicsViewModelFactory(requireActivity().application)
        viewModel= ViewModelProvider(this, viewModelFactory)[WorkoutBasicsViewModel::class.java]
        setUpRecyclerView(view)
        setUpWidgets(view)
        addItemFab.setOnClickListener {
            workoutEdtTxt.setText("")
            hideWidgets()
        }
        deleteAllBtn.setOnClickListener {
            viewModel.deleteAllWorkouts()
        }
        saveBtn.setOnClickListener {
            val workoutName = workoutEdtTxt.text.toString()
            val workoutsList = viewModel.allWorkouts.value as MutableList<WorkoutBasics>
            viewModel.insert( WorkoutBasics(name = workoutName, position = workoutsList.size) )
            hideWidgets()
        }
        viewModel.allWorkouts.observe(viewLifecycleOwner) { workouts ->
            recyclerAdapter.submitList(workouts)
        }
        return view
    }

    // SETUP //
    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = BasicsRecyclerAdapter(viewModel)
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