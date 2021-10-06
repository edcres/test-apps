package com.example.testfirestorev2.testhousematept2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.databinding.Housemate2RecyclerItemBinding

// see if it works with only one adapter, otherwise try: 1 for shopping, 1 for chores

class HousemateShoppingRecyclerAdapter :
    ListAdapter<ShoppingItem, HousemateShoppingRecyclerAdapter.HousemateRecyclerViewHolder>(
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

        private val housemate2ViewModel = Housemate2ViewModel()

        fun bind(item: ShoppingItem) {
            binding.apply {
                itemNameTxt.text = item.name
                itemQtyTxt.text = item.quantity.toString()
                itemNeededByTxt.text = item.neededBy
                itemWhereToGetTxt.text = item.purchaseLocation
                itemCostTxt.text = item.cost.toString()
                itemPriorityTxt.text = item.priority.toString()
                itemAddedByTxt.text = item.addedBy
                itemVolunteerEt.setText(item.volunteer)

                // send volunteer name to db
                sendVolunteerBtn.setOnClickListener{
                    housemate2ViewModel.sendShoppingVolunteerToDb(
                        item.name!!,
                        itemVolunteerEt.text.toString()
                    )
                }

                // delete list item
                deleteItemBtn.setOnClickListener {
                    housemate2ViewModel.deleteShoppingListItem(item.name!!)
                }
            }
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
