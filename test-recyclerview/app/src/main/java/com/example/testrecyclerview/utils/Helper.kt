package com.example.testrecyclerview.utils

import com.example.testrecyclerview.onewidget.Package

class Helper {

    fun fillUpRecyclerView(size: Int): MutableList<Package> {
        val itemsList = mutableListOf<Package>()
        for (i in 1..size) {
            val number = itemsList.size-1L
            val thisItem = Package(itemsList.size-1L, "Subtext $number")
            itemsList.add(thisItem)
        }
        return itemsList
    }
}