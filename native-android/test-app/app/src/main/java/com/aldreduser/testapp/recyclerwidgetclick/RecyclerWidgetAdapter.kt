package com.aldreduser.testapp.recyclerwidgetclick

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R
import com.aldreduser.testapp.basicrecyclerview.BasicRecyclerItem

class RecyclerWidgetAdapter(
    private val itemsList: List<BasicRecyclerItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerWidgetAdapter.ClickWidgetViewHolder>() {

    companion object {
        private const val TAG = "RecyclerWidgetAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ClickWidgetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_1, parent, false)

        return ClickWidgetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClickWidgetViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val itemSubTextText = "position in recycler: $position"

        holder.itemText1.text = currentItem.text1
        holder.itemSubText.text = itemSubTextText

        // todo: be careful about this below
        // bc the item widgets are recycled, the properties in a widget stays the same by default
        if (position == 0) {
            holder.itemText1.setBackgroundColor(Color.YELLOW)
        } else {
            // if the position is not 0, set the background color to it's original state
            // without this, some of the other items would stay yellow
            holder.itemText1.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    // without 'inner' this would be a static class in Java
    inner class ClickWidgetViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
        val itemSubText: TextView = itemView.findViewById(R.id.item_sub_text)

        init {
            itemText1.setOnClickListener {
                // In MVVM have a reference to the viewModel, and call a function to handle
                    // the click event
                val txtToDisplay = "Text was clicked."
                itemSubText.text = txtToDisplay
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Do this if check bc its possible to delete an item but click it before
                // it's completely animated off the recyclerview.
                onItemClickListener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        // Can pass more parameters and have the viewModel decide what to do with this data.
        fun onItemClick(position: Int)
    }
}
