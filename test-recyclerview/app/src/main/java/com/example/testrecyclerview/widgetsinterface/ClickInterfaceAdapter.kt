package com.example.testrecyclerview.widgetsinterface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R
import com.example.testrecyclerview.onewidget.Package

// interface to handle viewholder widgets clicks
// ListAdapter
// no dataBinding

class ClickInterfaceAdapter(
    private val packages: List<Package>,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Package, ClickInterfaceAdapter.ClickInterfaceViewHolder>(ClickInterfaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickInterfaceViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_1, parent, false)
        return ClickInterfaceViewHolder(itemView, packages, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ClickInterfaceViewHolder, position: Int) {
        packages[position]
    }

    class ClickInterfaceViewHolder constructor(
        itemView: View,
        packages: List<Package>,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
        private val itemText2: TextView = itemView.findViewById(R.id.item_sub_text)

        init {
            val thisPackage = packages[adapterPosition]
            itemText1.text = thisPackage.id.toString()
            itemText2.text = thisPackage.name
        }

        private fun widgetClicks() {
            // todo:
        }
    }

    interface OnItemClickListener {
        fun onViewHolderClick(position: Int)
    }

    class ClickInterfaceDiffCallback : DiffUtil.ItemCallback<Package>() {
        override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean {
            return oldItem == newItem
        }
    }
}