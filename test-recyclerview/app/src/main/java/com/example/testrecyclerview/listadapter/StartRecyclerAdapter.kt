package com.example.testrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.databinding.RecyclerItem1Binding
import com.example.testrecyclerview.listadapter.BasicRecyclerItem

private const val TAG = "StartAdapter_TAG"

class StartRecyclerAdapter :
    ListAdapter<BasicRecyclerItem, StartRecyclerAdapter.ClickWidgetViewHolder>(BasicItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ClickWidgetViewHolder.from(parent)

    override fun onBindViewHolder(holder: ClickWidgetViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ClickWidgetViewHolder private constructor(
        private val binding: RecyclerItem1Binding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(basicRecyclerItem: BasicRecyclerItem) {
            binding.apply {

                itemText1.text = basicRecyclerItem.text1
                itemSubText.text = basicRecyclerItem.text2
                deleteBtn.setOnClickListener {
                    // todo:
                }
                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ClickWidgetViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerItem1Binding.inflate(layoutInflater, parent, false)
                return ClickWidgetViewHolder(binding)
            }
        }
    }
}

class BasicItemDiffCallback : DiffUtil.ItemCallback<BasicRecyclerItem>() {

    override fun areItemsTheSame(oldItem: BasicRecyclerItem, newItem: BasicRecyclerItem) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: BasicRecyclerItem, newItem: BasicRecyclerItem) =
        oldItem == newItem
}