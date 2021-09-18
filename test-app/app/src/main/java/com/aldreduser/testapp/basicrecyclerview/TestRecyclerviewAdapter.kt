package com.aldreduser.testapp.basicrecyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R

// adapter for basic  recyclerview
class TestRecyclerviewAdapter(private val itemsList: List<BasicRecyclerItem>)
    : RecyclerView.Adapter<TestRecyclerviewAdapter.BasicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        // 'LayoutInflater' turns .xml layout files into view objects
        val itemView = LayoutInflater.from(parent.context)
             .inflate(R.layout.recycler_item_1, parent, false)

        return BasicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val itemSubTextText = "position in recycler: $position"

        holder.itemText1.text = currentItem.text1
        holder.itemSubText.text = itemSubTextText

        // todo: be careful about this below
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

    class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 'itemView' is an instance of the .xml recycler item
        val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
        val itemSubText: TextView = itemView.findViewById(R.id.item_sub_text)
    }
}
