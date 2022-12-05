package com.example.testrecyclerview.recyclerdotadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R
import com.example.testrecyclerview.onewidget.Package

class RecyDotAdaptAdapter(
    private val packages: List<Package>
) : RecyclerView.Adapter<RecyDotAdaptAdapter.RecyDotAdaptViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyDotAdaptViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_1, parent, false)
        return RecyDotAdaptViewHolder(itemView, packages)
    }

    override fun onBindViewHolder(holder: RecyDotAdaptViewHolder, position: Int) {
        packages[position]
    }

    override fun getItemCount(): Int = packages.size

    class RecyDotAdaptViewHolder constructor(itemView: View, packages: List<Package>) :
        RecyclerView.ViewHolder(itemView) {
            init {
                val thisPackage = packages[adapterPosition]
                val itemText1: TextView = itemView.findViewById(R.id.item_text_1)
                val itemText2: TextView = itemView.findViewById(R.id.item_sub_text)
                itemText1.text = thisPackage.id.toString()
                itemText2.text = thisPackage.name
            }
    }
}