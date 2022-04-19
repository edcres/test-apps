package com.example.testrecyclerview.listadapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// https://www.youtube.com/watch?v=wxoUkqXyi94
// To implement swipe to delete:
// Make this class and add the code to the view .kt file
//  - look for the 'Swipe to delete' comment


// another resource for swipe to delete while clicking a btn
// https://www.youtube.com/watch?v=1s4bMAyK7oM

// video for drag and dropping position
// https://www.youtube.com/watch?v=AY9KSp8sLzI
// implement this graddle library to decorate swipe to delete gesture
// 'it.xabaras.android:recyclerview-swipedecorator:1.2.3'

abstract class ItemMoveCallback : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

//    override fun getMovementFlags(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder
//    ): Int {
//        // Determine direction of the swipe on the recyclerItem
//        val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        return makeMovementFlags(0, swipeFlag)
//    }

    // onMove() is overwritten in the main activity
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        // can be implemented if there's drag and drop functionality in the recyclerView
//        return false
//    }
}