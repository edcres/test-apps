package com.example.testroom.basics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testroom.R
import com.example.testroom.basics.data.WorkoutBasics
import com.example.testroom.databinding.WorkoutRecyclerItemBinding

class BasicsRecyclerAdapter :
    ListAdapter<WorkoutBasics, BasicsRecyclerAdapter.WorkoutViewHolder>(WorkoutsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class WorkoutViewHolder private constructor(
        private val binding: WorkoutRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String?) {
            binding.workoutTxt.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WorkoutViewHolder {
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    WorkoutRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return WorkoutViewHolder(binding)
            }
        }
    }
    // WordsComparator defines how to compute if two words are the same or if the contents are the same.
    class WorkoutsComparator : DiffUtil.ItemCallback<WorkoutBasics>() {
        override fun areItemsTheSame(oldItem: WorkoutBasics, newItem: WorkoutBasics): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: WorkoutBasics, newItem: WorkoutBasics): Boolean {
            return oldItem.name == newItem.name
        }
    }
}