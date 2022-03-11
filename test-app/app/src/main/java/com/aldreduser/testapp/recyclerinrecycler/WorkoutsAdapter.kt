package com.aldreduser.testapp.recyclerinrecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.basicrecyclerview.entities.Workout

class WorkoutsAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Workout, WorkoutListAdapter.WorkoutsViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        return WorkoutsViewHolder.from(context, lifecycleOwner, parent)
    }

    override fun onBindViewHolder(holderWorkouts: WorkoutsViewHolder, position: Int) =
        holderWorkouts.bind(getItem(position))

    class WorkoutsViewHolder private constructor(
        private val context: Context,
        private val fragLifecycleOwner: LifecycleOwner,
        private val binding: WorkoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var setsAdapter: SetsAdapter

        fun bind(workout: Workout) {
            binding.apply {
                // TITLE //
                specificWorkoutInput.doAfterTextChanged {
                    workout.workoutName = it.toString()
                    viewModel.updateWorkoutName(workout)
                }
                // TITLE //
                // GROUP SETS //
                setsAdapter = SetsAdapter(viewModel, false)
                setListRecycler.adapter = setsAdapter
                viewModel.getSetsOfWorkout(workout.id)
                    .observe(fragLifecycleOwner) { theseSets ->
                        Log.d(GLOBAL_TAG, "WorkoutListAdapter observed:\n$theseSets")
                        setsAdapter.submitList(theseSets)
                    }

                // todo: I don't know what this code is for. I think it was a mistake.
//                viewModel.sets.observe(fragLifecycleOwner) {
//                    if (viewModel.workoutIdToEdit != null) {
//                        // Only call submitList() on the workout being edited
//                        if (workout.id == viewModel.workoutIdToEdit) {
//                            submitSets(workout, setsAdapter)
//                        }
//                    } else Log.e(GLOBAL_TAG, "workoutIdToEdit is null")
//                }

                // GROUP SETS //
                // SPINNER //
                if(workout.workoutGroup != FIRST_TAB_TITLE) chooseGroupBtn.visibility = View.VISIBLE
                val spinnerList = viewModel.groupNames
                spinnerList.add(NEW_GROUP)
                chooseGroupBtn.adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    spinnerList
                )
                chooseGroupBtn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val groupSelected = viewModel.groupNames[position]
                        chooseGroupBtn.visibility = View.GONE
                        if(groupSelected == NEW_GROUP) {
                            groupEtContainer.visibility = View.VISIBLE
                            newGroupDoneBtn.setOnClickListener {
                                viewModel.insertWorkoutGroup(
                                    WorkoutGroup(newGroupEt.text.toString())
                                )
                                workout.workoutGroup = groupSelected
                                viewModel.updateGroupOnWorkout(workout)
                            }
                        } else {
                            workout.workoutGroup = groupSelected
                            viewModel.updateGroupOnWorkout(workout)
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Log.i(GLOBAL_TAG, "Nothing was clicked.")
                    }
                }
                // SPINNER //

                viewModel.menuEditIsOn.observe(fragLifecycleOwner) { result ->
                    when (result) {
                        true -> {
                            editItemBtn.visibility = View.VISIBLE
                            removeItemBtn.visibility = View.VISIBLE
                        }
                        false -> {
                            editItemBtn.visibility = View.GONE
                            removeItemBtn.visibility = View.GONE
                        }
                    }
                }
                editItemBtn.setOnClickListener {
                    viewModel.workoutIdToEdit = workout.id
                    viewModel.setItemToEdit(workout)
                }
                removeItemBtn.setOnClickListener {
                    setsAdapter.submitList(viewModel.sets.value)
                    Log.d(GLOBAL_TAG, "submit clicked\n${viewModel.sets.value}")
//                    viewModel.removeWorkout(workout, workout.workoutGroup)
                }
                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(
                context: Context,
                fragLifecycleOwner: LifecycleOwner,
                parent: ViewGroup
            ): WorkoutsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorkoutItemBinding.inflate(layoutInflater, parent, false)
                return WorkoutsViewHolder(context, fragLifecycleOwner, binding)
            }
        }
    }
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.workoutName == newItem.workoutName
    }
    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}