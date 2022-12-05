package com.example.testrecyclerview.widgetsinterface

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R
import com.example.testrecyclerview.utils.Helper

// This is a recyclerview that uses multiple widgets with click listeners in each viewHolder (interface)

private const val TAG = "ManyWidgetsFrag__TAG"

class WidgetsInterfaceFragment : Fragment(), ClickInterfaceAdapter.OnItemClickListener {

    private val exampleList = Helper().fillUpRecyclerView(100)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_widgets_interface, container, false)
        val recyclerAdapter = ClickInterfaceAdapter(exampleList, this)
        recyclerView = view.findViewById(R.id.widgets_inter_recyclerview)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewHolderClick(position: Int, widget: Helper.WidgetClicked) {
        val thePackage = exampleList[position]
        val logTxt = when(widget) {
            Helper.WidgetClicked.ITEM_1 -> "Header"
            Helper.WidgetClicked.SUBTEXT -> "Subtext"
        }
        Log.d(TAG, "onViewHolderClick: $logTxt at position $position in package #${thePackage.id}")
    }
}