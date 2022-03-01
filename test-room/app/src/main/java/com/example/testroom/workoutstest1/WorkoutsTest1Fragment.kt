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
import com.example.testroom.findIdWithName
import com.example.testroom.workoutstest1.data.entities.WST1Group
import com.example.testroom.workoutstest1.data.entities.WST1Set
import com.example.testroom.workoutstest1.data.entities.WST1Workout

// I have to click some et twice to create them: Workout entity; Set entity

class WorkoutsTest1Fragment : Fragment() {

    private val fragmentTAG = "StartFragmentTAG"
    private var binding: FragmentWorkoutsTest1Binding? = null
    private val viewModel: WrkTst1ViewModel by activityViewModels()
    private var currentWorkout: WST1Workout? = null

    private var workoutLocked = false

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
        viewModel.startApplication(requireNotNull(this.activity).application)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner

            // test click listeners
            getGroupsBtn.setOnClickListener {
                viewModel.fetchGroupsClicked()
            }
            getWorkoutsBtn.setOnClickListener {
                viewModel.fetchWorkoutsClicked()
            }
            getSetsBtn.setOnClickListener {
                viewModel.fetchSetsClicked()
            }

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
            lockWorkoutBtn.setOnClickListener {
                workoutLocked = !workoutLocked
                if (workoutLocked) {
                    // now locked
                    lockWorkoutBtn.text = "unlock name"
                } else {
                    // now unlocked
                    lockWorkoutBtn.text = "lock name"
                }
            }
            // todo: Probably change this to see if it overrides another workout
            // (ie: calves inner, calves outer)
            workoutTitleEt.setOnClickListener {
                if (workoutTitleEt.text.toString() == "") {
                    val groupTitle = groupEt.text.toString().ifEmpty {
                        FIRST_TAB_TITLE
                    }
                    val thisWorkout = WST1Workout(
                        thisWorkoutName = "",
                        workoutGroup = groupTitle
                    )
                    val workoutId = viewModel.insertWorkout(thisWorkout)
                    workoutId.observe(viewLifecycleOwner) {
                        thisWorkout.id = it
                        Log.d(fragmentTAG, "workout clicked: workout id = ${thisWorkout.id}")
                        currentWorkout = thisWorkout
                    }
                }
            }
            workoutTitleEt.doAfterTextChanged {
                var chosenWorkout: WST1Workout? = currentWorkout
                if (!workoutLocked) {
                    chosenWorkout = findIdWithName(it.toString(), viewModel.workouts.value!!)
                }
                Log.d(fragmentTAG, "chosen workout: $chosenWorkout")
                if (chosenWorkout != null) {
                    currentWorkout = chosenWorkout
                    currentWorkout!!.thisWorkoutName = it.toString()
                    currentWorkout!!.workoutGroup = groupEt.text.toString()
                    Log.d(fragmentTAG, "workout id = ${currentWorkout!!.id},\tname: ${it.toString()}")
                    viewModel.updateWorkout(currentWorkout!!)
                }
            }
            repsTxt1.doAfterTextChanged {
                updateReps(1, it.toString(), weightTxt1.text.toString())
            }
            repsTxt2.doAfterTextChanged {
                updateReps(2, it.toString(), weightTxt2.text.toString())
            }
            repsTxt3.doAfterTextChanged {
                updateReps(3, it.toString(), weightTxt3.text.toString())
            }
            weightTxt1.doAfterTextChanged {
                updateWeight(1, repsTxt1.text.toString(), it.toString())
            }
            weightTxt2.doAfterTextChanged {
                updateWeight(2, repsTxt2.text.toString(), it.toString())
            }
            weightTxt3.doAfterTextChanged {
                updateWeight(3, repsTxt3.text.toString(), it.toString())
            }
            // Insert Sets
            repsTxt1.setOnClickListener {
                if(repsTxt1.text.isEmpty() && weightTxt1.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "reps1 clicked")
                    insertSet(1)
                }
            }
            repsTxt2.setOnClickListener {
                if(repsTxt2.text.isEmpty() && weightTxt2.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "reps2 clicked")
                    insertSet(2)
                }
            }
            repsTxt3.setOnClickListener {
                if(repsTxt3.text.isEmpty() && weightTxt3.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "reps3 clicked")
                    insertSet(3)
                }
            }
            weightTxt1.setOnClickListener {
                if(repsTxt1.text.isEmpty() && weightTxt1.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "weight1 clicked")
                    insertSet(1)
                }
            }
            weightTxt2.setOnClickListener {
                if(repsTxt2.text.isEmpty() && weightTxt2.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "weight2 clicked")
                    insertSet(2)
                }
            }
            weightTxt3.setOnClickListener {
                if(repsTxt3.text.isEmpty() && weightTxt3.text.isEmpty() && workoutTitleEt.text.toString().isNotEmpty()) {
                    Log.d(fragmentTAG, "weight3 clicked")
                    insertSet(3)
                }
            }
        }
        setUpObservers()
    }

    private fun insertSet(set: Int) {
        Log.d(fragmentTAG, "workout inserted: set = $set\n .")
        val workoutId = currentWorkout!!.id
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.insertWorkoutSet(
            WST1Set(
                workoutPlusSet = "$workoutId$set",
                workoutId,
                workoutName,
                set,
                0,
                0.0
            )
        )
    }

    private fun updateReps(set: Int, reps: String, weight: String) {
        Log.d(fragmentTAG, "reps to update: set = $set\n reps: $reps.")
        val workoutId = currentWorkout!!.id
        val newReps = reps.ifEmpty { "0" }
        val newWeight = weight.ifEmpty { "0.0" }
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.updateRep(
            WST1Set(
                workoutPlusSet = "$workoutId$set",
                workoutId,
                workoutName,
                set,
                newReps.toInt(),
                newWeight.toDouble()
            )
        )
    }

    private fun updateWeight(set: Int, reps: String, weight: String) {
        Log.d(fragmentTAG, "weight to update: set = $set\n, weight: $weight.")
        val workoutId = currentWorkout!!.id
        val newReps = reps.ifEmpty { "0" }
        val newWeight = weight.ifEmpty { "0.0" }
        val workoutName = binding!!.workoutTitleEt.text.toString()
        viewModel.updateRep(
            WST1Set(
                workoutPlusSet = "$workoutId$set",
                workoutId,
                workoutName,
                set,
                newReps.toInt(),
                newWeight.toDouble()
            )
        )
    }

    private fun setUpObservers() {
        // todo: display the lists in a more legible way
        viewModel.groups.observe(viewLifecycleOwner) {
            var groupsString = ""
            it.forEach { group -> groupsString = "$groupsString\n$group" }
            Log.d(fragmentTAG, "groups observed: ${it.size}$groupsString\n.")
        }
        viewModel.workouts.observe(viewLifecycleOwner) {
            var workoutsString = ""
            it.forEach { workout -> workoutsString = "$workoutsString\n$workout" }
            Log.d(fragmentTAG, "workouts observed: ${it.size}$workoutsString\n.")
        }
        viewModel.sets.observe(viewLifecycleOwner) {
            var setsString = ""
            it.forEach { set -> setsString = "$setsString\n$set" }
            Log.d(fragmentTAG, "sets observed: ${it.size}$setsString\n.")
        }
    }
}