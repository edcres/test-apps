package com.example.testrecyclerview.utils

import android.util.Log
import com.example.testrecyclerview.onewidget.Package

class Helper {

    enum class WidgetClicked { ITEM_1, SUBTEXT }

    fun fillUpRecyclerView(size: Int): MutableList<Package> {
        val itemsList = mutableListOf<Package>()
        for (i in 1..size) {
            val thisItem = Package(i.toLong(), "Subtext $i")
            itemsList.add(thisItem)
        }
        return itemsList
    }
}