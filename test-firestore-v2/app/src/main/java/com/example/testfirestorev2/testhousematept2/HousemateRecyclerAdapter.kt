package com.example.testfirestorev2.testhousematept2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.databinding.Housemate2RecyclerItemBinding

// see if it works with only one adapter, otherwise try: 1 for shopping, 1 for chores

class HousemateRecyclerAdapter : ListAdapter<ShoppingItem,
        HousemateRecyclerAdapter.HousemateRecyclerViewHolder>(ShoppingItemDiffCallback()) {

    private lateinit var shoppingItemsList: MutableList<ShoppingItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousemateRecyclerViewHolder {
        return HousemateRecyclerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HousemateRecyclerViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class HousemateRecyclerViewHolder private constructor(val binding: Housemate2RecyclerItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        // how does this adapter (with dataBinding) know which how may items to have
        // maybe set up the observer in the view, and the view contacts the adapter
        // figure out how to get db changes from db to here
        // figure out how to get here changes from here to db

        fun bind(item: ShoppingItem) {
            binding.itemNameEt
            binding.itemNeededByEt
            binding.itemPriorityEt
        }

        companion object {
            fun from(parent: ViewGroup): HousemateRecyclerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = Housemate2RecyclerItemBinding.inflate(layoutInflater, parent, false)
                return HousemateRecyclerViewHolder(binding)
            }
        }

    }

    // HELPER FUNCTIONS //
    fun submitShoppingList(shoppingList: MutableList<ShoppingItem>) {
        shoppingItemsList = shoppingList
    }
}

class ShoppingItemDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
//        return oldItem.id == newItem.id       idk why it used ot have the .id
    }

    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }

}
