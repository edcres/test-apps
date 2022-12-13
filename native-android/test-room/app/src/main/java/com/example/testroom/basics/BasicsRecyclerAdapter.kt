package com.example.testroom.basics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testroom.basics.data.WorkoutBasics
import com.example.testroom.databinding.WorkoutRecyclerItemBinding

class BasicsRecyclerAdapter(val viewModel: WorkoutBasicsViewModel) :
    ListAdapter<WorkoutBasics, BasicsRecyclerAdapter.WorkoutViewHolder>(WorkoutsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder.create(viewModel, parent)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class WorkoutViewHolder private constructor(
        val viewModel: WorkoutBasicsViewModel,
        private val binding: WorkoutRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutBasics) {
            binding.apply {
                workoutTxt.text = item.name
                upBtn.setOnClickListener{
                    viewModel.moveRecyclerItems(moveUp = true, item)
                }
                downBtn.setOnClickListener {
                    viewModel.moveRecyclerItems(moveUp = false, item)
                }
            }
        }

        companion object {
            fun create(viewModel: WorkoutBasicsViewModel, parent: ViewGroup): WorkoutViewHolder {
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    WorkoutRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return WorkoutViewHolder(viewModel, binding)
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