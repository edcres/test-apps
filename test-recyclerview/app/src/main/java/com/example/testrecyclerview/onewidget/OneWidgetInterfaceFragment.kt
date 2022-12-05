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
import com.example.testrecyclerview.utils.Helper

// This is a recyclerview that uses one widget with a click listener on each viewHolder (interface)

private const val TAG = "OneWidgetFrag__TAG"

class OneWidgetInterfaceFragment : Fragment(), OneWdgtIntfcAdapter.OnItemClickListener {

    private val exampleList = Helper().fillUpRecyclerView(100)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one_widget_interface, container, false)
        val recyclerAdapter = OneWdgtIntfcAdapter(exampleList, this)
        recyclerView = view.findViewById(R.id.one_inter_recyclerview)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewHolderClick(position: Int) {
        val thePackage = exampleList[position]
        Log.d(TAG, "onViewHolderClick: clicked ${thePackage.id} \t ${thePackage.name}")
    }
}