package com.example.testrecyclerview.listadapter

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// https://www.youtube.com/watch?v=wxoUkqXyi94
// To implement swipe to delete:
// Make this class and add the code to the view .kt file
//  - look for the 'Swipe to delete' comment

// copy 'RecyclerViewSwipeDecorator' class from GitHub to decorate swipe to delete gesture
// https://github.com/xabaras/RecyclerViewSwipeDecorator/blob/master/recyclerview-swipedecorator/src/main/java/it/xabaras/android/recyclerview/swipedecorator/RecyclerViewSwipeDecorator.java

// another resource for swipe to delete while clicking a btn
// https://www.youtube.com/watch?v=1s4bMAyK7oM

// video for drag and dropping position
// https://www.youtube.com/watch?v=AY9KSp8sLzI

abstract class ItemMoveCallback(val deleteColor: Int, val deleteIcon: Int) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightBackgroundColor(deleteColor)
            .addSwipeRightActionIcon(deleteIcon)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

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