package com.example.testrecyclerview.onewidget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R

// interface to handle viewholder clicks
// ListAdapter
// no dataBinding

class OneWdgtIntfcAdapter(
    private val packages: List<Package>,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Package, OneWdgtIntfcAdapter.OneWdgtIntfcViewHolder>(OneWdgtIntfcDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneWdgtIntfcViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_1, parent, false)
        return OneWdgtIntfcViewHolder(itemView, packages, onItemClickListener)
    }

    override fun onBindViewHolder(holder: OneWdgtIntfcViewHolder, position: Int) {
        packages[position]
    }

    class OneWdgtIntfcViewHolder constructor(
        itemView: View,
        packages: List<Package>,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
            val thisPackage = packages[adapterPosition]

            val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
            val itemText2: TextView = itemView.findViewById(R.id.item_sub_text)
            itemText1.text = thisPackage.id.toString()
            itemText2.text = thisPackage.name
        }

        // Could use lambdas to handle clicks but this (interfaces) is simpler
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // do this bc its possible to delete an item but click it before it's completely
                // animated off the recyclerview
                onItemClickListener.onViewHolderClick(position)
            }
        }
    }

    // this interface is implemented by the class that calls this adapter
    interface OnItemClickListener {
        fun onViewHolderClick(position: Int)
    }

    class OneWdgtIntfcDiffCallback : DiffUtil.ItemCallback<Package>() {
        override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem == newItem
        }
    }
}