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
import com.example.testrecyclerview.utils.Helper

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
        val itemText1: TextView = holder.itemView.findViewById(R.id.item_text_1)
        val itemText2: TextView = holder.itemView.findViewById(R.id.item_sub_text)
        itemText1.text = packages[position].id.toString()
        itemText2.text = packages[position].name
    }

    class ClickInterfaceViewHolder constructor(
        itemView: View,
        packages: List<Package>,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
        private val itemText2: TextView = itemView.findViewById(R.id.item_sub_text)

        init {
            // todo: maybe bug: for some reason adapterPosition is always -1, idk why
            // when 'adapterPosition' = -1 = NO_POSITION
            // It works with on click listeners so maybe there's a refractory period to get everything set up
//            val thisPackage = packages[adapterPosition]
//            itemText1.text = thisPackage.id.toString()
//            itemText2.text = thisPackage.name

            itemText1.setOnClickListener {
                clickWidget(Helper.WidgetClicked.ITEM_1)
            }
            itemText2.setOnClickListener {
                clickWidget(Helper.WidgetClicked.SUBTEXT)
            }
        }

        private fun clickWidget(widget: Helper.WidgetClicked) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // do this bc its possible to delete an item but click it before it's completely
                // animated off the recyclerview
                onItemClickListener.onViewHolderClick(position, widget)
            }
        }
    }

    interface OnItemClickListener {
        fun onViewHolderClick(position: Int, widget: Helper.WidgetClicked)
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