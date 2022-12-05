package com.example.testrecyclerview.recyclerdotadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R
import com.example.testrecyclerview.utils.Helper

// test things like updating all or updating one item or updating deletions
// itemChanged(), itemRemoved(), itemAdded()

private const val TAG = "Recy.AdapterFrag__TAG"

class RecyDotAdapterFragment : Fragment() {

    private val exampleList = Helper().fillUpRecyclerView(100)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recy_dot_adapter, container, false)
        val recyclerAdapter = RecyDotAdaptAdapter(exampleList)
        recyclerView = view.findViewById(R.id.recy_dot_recycler)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}