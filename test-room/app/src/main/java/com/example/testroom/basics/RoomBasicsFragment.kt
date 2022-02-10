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

// todo: check if the id is autoIncrementing

class RoomBasicsFragment : Fragment() {

    private val viewModelFactory = WorkoutBasicsViewModelFactory(requireActivity().application)
    private val viewModel =
        ViewModelProvider(this, viewModelFactory)[WorkoutBasicsViewModel::class.java]

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: BasicsRecyclerAdapter
    private lateinit var addItemFab: FloatingActionButton
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
        addItemFab.setOnClickListener {
            hideWidgets()
        }
        saveBtn.setOnClickListener {
            val workoutName = workoutEdtTxt.text.toString()
            viewModel.insert(WorkoutBasics(name = workoutName))
            hideWidgets()
        }
        viewModel.allWords.observe(viewLifecycleOwner) { workouts ->
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
            addItemContainer.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            addItemFab.visibility = View.VISIBLE
            addItemContainer.visibility = View.GONE
        }
    }
}