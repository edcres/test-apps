package com.example.testrecyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StartFragment : Fragment(), StartRecyclerAdapter.OnItemClickListener {

    private lateinit var refreshBtn: Button
    private lateinit var startRecyclerview: RecyclerView
    private val exampleList = fillUpRecyclerView(8)
    private var recyclerAdapter = StartRecyclerAdapter(exampleList, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val animController = LayoutAnimationController(AnimationUtils
            .loadAnimation(requireContext(), R.anim.item_anim))
        animController.delay = 0.20f
        animController.order = LayoutAnimationController.ORDER_NORMAL

        refreshBtn = view.findViewById(R.id.refresh_btn)
        startRecyclerview = view.findViewById(R.id.start_recyclerview)
        startRecyclerview.layoutAnimation = animController
        startRecyclerview.adapter = recyclerAdapter
        startRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        refreshBtn.setOnClickListener {
            startRecyclerview.startLayoutAnimation()
        }

        return view
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Item position: $position clicked", Toast.LENGTH_SHORT)
            .show()
        val clickedItem = exampleList[position]
        clickedItem.text1 = "Clicked"
        recyclerAdapter.notifyItemChanged(position)
    }

    private fun fillUpRecyclerView(size: Int): MutableList<BasicRecyclerItem> {
        val itemsList = mutableListOf<BasicRecyclerItem>()
        for (i in 1..size) {
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext $i")  //position in mutableList()
            itemsList.add(thisItem)
        }
        return itemsList
    }
}