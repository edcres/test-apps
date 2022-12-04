package com.example.testrecyclerview.onewidget

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R

// This is a recyclerview that uses one widget with a click listener on each viewHolder (interface)

private const val TAG = "OneClickFrag__TAG"

class OneWidgetInterfaceFragment : Fragment(), OneWdgtIntfcAdapter.OnItemClickListener {

    private val exampleList = fillUpRecyclerView(100)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one_widget_interface, container, false)
        val recyclerAdapter = OneWdgtIntfcAdapter(exampleList, this)
        recyclerView = view.findViewById(R.id.oneInterRecyclerview)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    // HELPER FUNCTIONS //
    private fun fillUpRecyclerView(size: Int): MutableList<Package> {
        val itemsList = mutableListOf<Package>()
        for (i in 1..size) {
            val number = itemsList.size-1L
            val thisItem = Package(itemsList.size-1L, "Subtext $number")
            itemsList.add(thisItem)
        }
        return itemsList
    }

    override fun onViewHolderClick(position: Int) {
        val thePackage = exampleList[position]
        Log.d(TAG, "onViewHolderClick: clicked ${thePackage.id} \t ${thePackage.name}")
    }
}