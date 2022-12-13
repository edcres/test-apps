package com.aldreduser.testapp.recyclerviewclicklistener

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R
import com.aldreduser.testapp.basicrecyclerview.BasicRecyclerItem

class RecyclerClickListenerAdapter(
    private val itemsList: List<BasicRecyclerItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerClickListenerAdapter.ClickListenerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickListenerViewHolder {
        // 'LayoutInflater' turns .xml layout files into view objects
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_1, parent, false)

        return ClickListenerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClickListenerViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val itemSubTextText = "position in recycler when added: $position"

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
    inner class ClickListenerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // 'itemView' is an instance of the .xml recycler item
        val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
        val itemSubText: TextView = itemView.findViewById(R.id.item_sub_text)

        // have click listeners here. These can be in onBindViewHolder but that's inefficient
        init {
            itemView.setOnClickListener(this)
        }

        // Could use lambdas to handle clicks but this (interfaces) is simpler
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // do this bc its possible to delete an item but click it before it's completely
                // animated off the recyclerview
                onItemClickListener.onItemClick(position)
            }
        }
    }

    // this interface is implemented by the file that calls this adapter
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
