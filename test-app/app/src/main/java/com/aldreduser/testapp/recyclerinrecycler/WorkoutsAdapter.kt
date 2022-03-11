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
import com.aldreduser.testapp.FIRST_TAB_TITLE
import com.aldreduser.testapp.GLOBAL_TAG
import com.aldreduser.testapp.basicrecyclerview.entities.Workout
import com.aldreduser.testapp.databinding.WorkoutItemBinding

class WorkoutsAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Workout, WorkoutsAdapter.WorkoutsViewHolder>(WorkoutDiffCallback()) {

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
                setsAdapter = SetsAdapter(false)
                setListRecycler.adapter = setsAdapter
                viewModel.getSetsOfWorkout(workout.id)
                    .observe(fragLifecycleOwner) { theseSets ->
                        Log.d(GLOBAL_TAG, "WorkoutListAdapter observed:\n$theseSets")
                        setsAdapter.submitList(theseSets)
                    }

                // GROUP SETS //

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