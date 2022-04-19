package com.example.testrecyclerview.listadapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// https://www.youtube.com/watch?v=wxoUkqXyi94
// To implement swipe to delete:
// Make this class and add the code to the view .kt file
//  - look for the 'Swipe to delete' comment

abstract class SwipeToDeleteCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // Determine direction of the swipe on the recyclerItem
        val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // can be implemented if there's drag and drop functionality in the recyclerView
        return false
    }
}