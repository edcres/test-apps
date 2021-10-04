package com.example.testfirestorev2.testhousematept2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.databinding.Housemate2RecyclerItemBinding

// see if it works with only one adapter, otherwise try: 1 for shopping, 1 for chores

class HousemateRecyclerAdapter :
    ListAdapter<ShoppingItem, HousemateRecyclerAdapter.HousemateRecyclerViewHolder>(
        ShoppingItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousemateRecyclerViewHolder {
        return HousemateRecyclerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HousemateRecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HousemateRecyclerViewHolder (private val binding: Housemate2RecyclerItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        // maybe set up the observer in the view, and the view contacts the adapter
        // figure out how to get db changes from db to here
        // figure out how to get here changes from here to db

        fun bind(item: ShoppingItem) {
            binding.apply {
                itemNameEt.text = item.name
                itemNeededByEt.text = item.neededBy.toString()
                itemPriorityEt.text = item.priority.toString()
            }

            Log.d("HsMtTest2TAG", item.neededBy.toString())
            Log.d("HsMtTest2TAG", item.neededBy!!)
        }

        companion object {
            fun from(parent: ViewGroup): HousemateRecyclerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = Housemate2RecyclerItemBinding.inflate(layoutInflater, parent, false)
                return HousemateRecyclerViewHolder(binding)
            }
        }

    }
}

class ShoppingItemDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {

    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }

}
