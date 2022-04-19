package com.example.testrecyclerview.listadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.listadapter.BasicRecyclerItem

private const val TAG = "LAFrag_TAG"

class LAFragment : Fragment() {

    private lateinit var refreshBtn: Button
    private lateinit var btn2: Button
    private lateinit var startRecyclerview: RecyclerView
    private var recyclerAdapter = StartRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_l_a, container, false)





        val animController = LayoutAnimationController(AnimationUtils
            .loadAnimation(requireContext(), R.anim.item_anim))
        animController.delay = 0.20f
        animController.order = LayoutAnimationController.ORDER_NORMAL

        refreshBtn = view.findViewById(R.id.refresh_btn)
        btn2 = view.findViewById(R.id.btn_2)
        startRecyclerview = view.findViewById(R.id.start_recyclerview)
        startRecyclerview.layoutAnimation = animController
        startRecyclerview.adapter = recyclerAdapter
        startRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        recyclerAdapter.submitList(fillUpRecyclerView(5))

        btn2.setOnClickListener {
            val newList = fillUpRecyclerView(5)
            newList.removeAt(1)
            newList.removeAt(1)
            recyclerAdapter.submitList(newList)
        }

        refreshBtn.setOnClickListener {
            startRecyclerview.startLayoutAnimation()
        }





        return view
    }

    private fun fillUpRecyclerView(size: Int): MutableList<BasicRecyclerItem> {
        val itemsList = mutableListOf<BasicRecyclerItem>()
        for (i in 1..size) {
            val thisItem = BasicRecyclerItem("Item #$i", "Subtext $i")  //position in mutableList()
            itemsList.add(thisItem)
        }
        return itemsList
    }

//    private fun copyList(oldList: MutableList<BasicRecyclerItem>): MutableList<BasicRecyclerItem> {
//        val newList = mutableListOf<BasicRecyclerItem>()
//        oldList.forEach {
//            newList.add(it)
//        }
//        return newList
//    }
}