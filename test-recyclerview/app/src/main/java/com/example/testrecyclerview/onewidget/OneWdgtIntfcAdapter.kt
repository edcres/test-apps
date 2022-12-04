package com.example.testrecyclerview.onewidget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R

// List adapter and interface to handle viewholder clicks

class OneWdgtIntfcAdapter(
    private val packages: List<Package>,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Package, OneWdgtIntfcAdapter.OneWdgtIntfcViewHolder>(OneWdgtIntfcViewHolderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneWdgtIntfcViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_1, parent, false)
        return OneWdgtIntfcViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: OneWdgtIntfcViewHolder, position: Int) {
        packages[position]
    }

    class OneWdgtIntfcViewHolder constructor(
        itemView: View,
        private var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
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

    class OneWdgtIntfcViewHolderDiffCallback : DiffUtil.ItemCallback<Package>() {
        override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem == newItem
        }
    }
}