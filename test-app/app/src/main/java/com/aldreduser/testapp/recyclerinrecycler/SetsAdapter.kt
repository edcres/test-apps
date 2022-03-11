package com.aldreduser.testapp.recyclerinrecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.GLOBAL_TAG
import com.aldreduser.testapp.basicrecyclerview.entities.WorkoutSet
import com.aldreduser.testapp.databinding.SetLinearLayoutBinding

class SetsAdapter(
    private val setAreRemoved: Boolean
) : ListAdapter<WorkoutSet, SetsAdapter.SetsViewHolder>(SetDiffCallback()) {

    init {
        Log.d(GLOBAL_TAG, "SetsAdapter initialized")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        Log.d(GLOBAL_TAG, "onCreateViewHolder: called")
        return SetsViewHolder.from(setAreRemoved, parent)
    }

    //    override fun onBindViewHolder(holderWorkouts: SetsViewHolder, position: Int) =
    //        holderWorkouts.bind(getItem(position))
    override fun onBindViewHolder(holderWorkouts: SetsViewHolder, position: Int) {
        Log.d(GLOBAL_TAG, "onBindViewHolder: called")
        return holderWorkouts.bind(getItem(position))
    }

    class SetsViewHolder private constructor(
        private val setAreRemoved: Boolean,
        private val binding: SetLinearLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workoutSet: WorkoutSet) {
            Log.d(GLOBAL_TAG, "set ${workoutSet.set} called from setsAdapter.")
            binding.apply {
                if(setAreRemoved) {
                    // If sets can be removed
                    removeSetBtn.visibility = View.VISIBLE
                    spacer.visibility = View.VISIBLE
                    removeSetBtn.setOnClickListener {
                        viewModel.removeSet(workoutSet)
                    }
                }

                setText.setText(workoutSet.set.toString())
                repsText.setText(workoutSet.reps.toString())
                weightText.setText(workoutSet.weight.toString())

                repsText.doAfterTextChanged {
                    workoutSet.reps = it.toString().toInt()
                    viewModel.updateSet(workoutSet)
                }
                weightText.doAfterTextChanged {
                    workoutSet.weight = it.toString().toDouble()
                    viewModel.updateSet(workoutSet)
                }
            }
        }

        companion object {
            fun from(
                setAreRemoved: Boolean,
                parent: ViewGroup
            ): SetsViewHolder {
                Log.d(GLOBAL_TAG, "sets adapter companion object: called")
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SetLinearLayoutBinding
                    .inflate(layoutInflater, parent, false)
                return SetsViewHolder(setAreRemoved, binding)
            }
        }
    }
}

class SetDiffCallback : DiffUtil.ItemCallback<WorkoutSet>() {
    override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
        return oldItem == newItem
    }
}