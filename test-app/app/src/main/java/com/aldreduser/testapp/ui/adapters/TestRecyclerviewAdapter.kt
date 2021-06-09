package com.aldreduser.testapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldreduser.testapp.R

// adapter for test recyclerview
class TestRecyclerviewAdapter() : RecyclerView.Adapter<TestRecyclerviewAdapter.ThisViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

        return ThisViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThisViewHolder, position: Int) {
//        val currentItem = exampleList[position]
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ThisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //
    }
}
