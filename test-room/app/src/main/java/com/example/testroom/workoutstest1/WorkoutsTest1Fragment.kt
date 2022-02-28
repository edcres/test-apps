package com.example.testroom.workoutstest1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.testroom.FIRST_TAB_TITLE
import com.example.testroom.databinding.FragmentWorkoutsTest1Binding
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout

class WorkoutsTest1Fragment : Fragment() {

    private val fragmentTAG = "StartFragmentTAG"
    private var binding: FragmentWorkoutsTest1Binding? = null
    private val viewModel: WrkTst1ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutsTest1Binding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo update the view widgets when an item is sent to the db (maybe it's done automatically)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            clearTextsBtn.setOnClickListener {
                groupEt.text.clear()
                workoutTitleEt.text.clear()
                repsTxt1.text.clear()
                repsTxt2.text.clear()
                repsTxt3.text.clear()
                weightTxt1.text.clear()
                weightTxt2.text.clear()
                weightTxt3.text.clear()
            }
            addGroupBtn.setOnClickListener {
                viewModel.insertWorkoutGroup(WST1Group(groupEt.text.toString()))
            }
            // todo: Probably change this to see if it overrides another workout
            // (ie: calves inner, calves outer)
            workoutTitleEt.setOnClickListener {
                if (workoutTitleEt.text.toString() == "") {
                    val groupTitle = if(groupEt.text.toString().isEmpty()) {
                        FIRST_TAB_TITLE
                    } else groupEt.text.toString()
                    viewModel.insertWorkout(
                        WST1Workout(
                            "",
                            groupTitle
                        )
                    )
                }
            }
            workoutTitleEt.doAfterTextChanged {
                val thisWorkout = WST1Workout(
                    workoutTitleEt.text.toString(),
                    groupEt.text.toString()
                )
                viewModel.updateTitle(thisWorkout)
            }
            repsTxt1.doAfterTextChanged {
                updateReps(1, it.toString().toInt(), weightTxt1.toString().toDouble())
            }
            repsTxt2.doAfterTextChanged {
                updateReps(2, it.toString().toInt(), weightTxt2.toString().toDouble())
            }
            repsTxt3.doAfterTextChanged {
                updateReps(3, it.toString().toInt(), weightTxt3.toString().toDouble())
            }
            weightTxt1.doAfterTextChanged {
                updateWeight(1, repsTxt1.toString().toInt(), it.toString().toDouble())
            }
            weightTxt2.doAfterTextChanged {
                updateWeight(2, repsTxt2.toString().toInt(), it.toString().toDouble())
            }
            weightTxt3.doAfterTextChanged {
                updateWeight(3, repsTxt3.toString().toInt(), it.toString().toDouble())
            }
            // Insert Sets
            repsTxt1.setOnClickListener {
                if(repsTxt1.text.isEmpty() && weightTxt1.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(1)
                }
            }
            repsTxt2.setOnClickListener {
                if(repsTxt2.text.isEmpty() && weightTxt2.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(2)
                }
            }
            repsTxt3.setOnClickListener {
                if(repsTxt3.text.isEmpty() && weightTxt3.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(3)
                }
            }
            weightTxt1.setOnClickListener {
                if(repsTxt1.text.isEmpty() && weightTxt1.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(1)
                }
            }
            weightTxt2.setOnClickListener {
                if(repsTxt2.text.isEmpty() && weightTxt2.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(2)
                }
            }
            weightTxt3.setOnClickListener {
                if(repsTxt3.text.isEmpty() && weightTxt3.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    insertSet(3)
                }
            }
        }
        setUpObservers()
    }

    private fun insertSet(setNum: Int) {
        Log.d(fragmentTAG, "workout inserted: set = $setNum\n .")
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.insertWorkoutSet(
            WST1Set(
                workoutPlusSet = "$workoutName$setNum",
                workoutName,
                setNum,
                0,
                0.0
            )
        )
    }

    private fun updateReps(set: Int, reps: Int, weight: Double) {
        Log.d(fragmentTAG, "reps updated: set = $set\n .")
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.updateRep(
            WST1Set(
                workoutPlusSet = "$workoutName$set",
                workoutName,
                set,
                reps,
                weight
            )
        )
    }

    private fun updateWeight(set: Int, reps: Int, weight: Double) {
        Log.d(fragmentTAG, "weight updated: set = $set\n .")
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.updateRep(
            WST1Set(
                workoutPlusSet = "$workoutName$set",
                workoutName,
                set,
                reps,
                weight
            )
        )
    }

    private fun setUpObservers() {
        // todo: display the lists in a more legible way
        viewModel.groups.observe(viewLifecycleOwner) {
            Log.d(fragmentTAG, "groups: ${it.size}\n\n$it")
        }
        viewModel.workouts.observe(viewLifecycleOwner) {
            Log.d(fragmentTAG, "workouts: ${it.size}\n\n$it")
        }
        viewModel.sets.observe(viewLifecycleOwner) {
            Log.d(fragmentTAG, "sets: ${it.size}\n\n$it")
        }
    }
}