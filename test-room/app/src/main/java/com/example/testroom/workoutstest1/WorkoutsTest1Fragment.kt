package com.example.testroom.workoutstest1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.testroom.R
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
                    viewModel.insertWorkout(
                        WST1Workout(
                            "",
                            groupEt.text.toString()
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
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateRep(
                    WST1Set(
                        workoutPlusSet = "$workoutName$1",
                        workoutName,
                        set = 1,
                        reps = it.toString().toInt(),
                        weight = weightTxt1.toString().toDouble()
                    )
                )
            }
            repsTxt2.doAfterTextChanged {
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateRep(
                    WST1Set(
                        workoutPlusSet = "$workoutName$2",
                        workoutName,
                        2,
                        reps = it.toString().toInt(),
                        weight = weightTxt2.toString().toDouble()
                    )
                )
            }
            repsTxt3.doAfterTextChanged {
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateRep(
                    WST1Set(
                        workoutPlusSet = "$workoutName$3",
                        workoutName,
                        3,
                        reps = it.toString().toInt(),
                        weight = weightTxt3.toString().toDouble()
                    )
                )
            }
            weightTxt1.doAfterTextChanged {
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateWeight(
                    WST1Set(
                        workoutPlusSet = "$workoutName$1",
                        workoutName,
                        1,
                        reps = repsTxt1.toString().toInt(),
                        weight = it.toString().toDouble()
                    )
                )
            }
            weightTxt2.doAfterTextChanged {
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateWeight(
                    WST1Set(
                        workoutPlusSet = "$workoutName$2",
                        workoutName,
                        2,
                        reps = repsTxt2.toString().toInt(),
                        weight = it.toString().toDouble()
                    )
                )
            }
            weightTxt3.doAfterTextChanged {
                val workoutName = workoutTitleEt.text.toString()
                viewModel.updateWeight(
                    WST1Set(
                        workoutPlusSet = "$workoutName$3",
                        workoutName,
                        3,
                        reps = repsTxt3.toString().toInt(),
                        weight = it.toString().toDouble()
                    )
                )
            }
        }
        setUpObservers()
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