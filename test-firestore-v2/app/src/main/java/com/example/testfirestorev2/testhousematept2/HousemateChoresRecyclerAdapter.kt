package com.example.testfirestorev2.testhousematept2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testfirestorev2.databinding.Housemate2RecyclerItemBinding

class HousemateChoresRecyclerAdapter :
    ListAdapter<ChoresItem, HousemateChoresRecyclerAdapter.HousemateRecyclerViewHolder>(
        ChoreItemDiffCallback()
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

        fun bind(item: ChoresItem) {
            binding.apply {
                itemQtyTxt.text = "Difficulty"
                itemWhereToGetTxt.visibility = View.GONE
                itemCostTxt.visibility = View.GONE
                itemWhereToGetLabel.visibility = View.GONE
                itemCostLabel.visibility = View.GONE

                itemNameTxt.text = item.name
                itemQtyTxt.text = item.difficulty.toString()        // 'itemQtyTxt' will act as the difficulty
                itemNeededByTxt.text = item.neededBy
                itemPriorityTxt.text = item.priority.toString()
                itemAddedByTxt.text = item.addedBy
                itemVolunteerEt.setText(item.volunteer)

                sendVolunteerBtn.setOnClickListener {
                    housemate2ViewModel.sendChoresVolunteerToDb(
                        item.name!!,
                        itemVolunteerEt.text.toString()
                    )
                }

                deleteItemBtn.setOnClickListener {
                    housemate2ViewModel.deleteChoresListItem(item.name!!)
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

class ChoreItemDiffCallback : DiffUtil.ItemCallback<ChoresItem>() {

    override fun areItemsTheSame(oldItem: ChoresItem, newItem: ChoresItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChoresItem, newItem: ChoresItem): Boolean {
        return oldItem == newItem
    }

}
