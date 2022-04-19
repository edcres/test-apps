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
import com.example.testrecyclerview.R
import com.example.testrecyclerview.StartRecyclerAdapter

private const val TAG = "LAFrag_TAG"

class LAFragment : Fragment() {

    private lateinit var refreshBtn: Button
    private lateinit var addBtn: Button
    private lateinit var removeBtn: Button
    private lateinit var startRecyclerview: RecyclerView
    private var recyclerAdapter = StartRecyclerAdapter()
    private val viewModel = LAViewModel()

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
        addBtn = view.findViewById(R.id.add_btn)
        removeBtn = view.findViewById(R.id.remove_btn)
        startRecyclerview = view.findViewById(R.id.start_recyclerview)
        startRecyclerview.layoutAnimation = animController
        startRecyclerview.adapter = recyclerAdapter
        startRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.basicItems.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it.toList())
        }

        addBtn.setOnClickListener {
            viewModel.addItem()
        }

        removeBtn.setOnClickListener {
            viewModel.removeItemAt(1)
        }

        refreshBtn.setOnClickListener {
            startRecyclerview.startLayoutAnimation()
        }

        return view
    }

//    private fun copyList(oldList: MutableList<BasicRecyclerItem>): MutableList<BasicRecyclerItem> {
//        val newList = mutableListOf<BasicRecyclerItem>()
//        oldList.forEach {
//            newList.add(it)
//        }
//        return newList
//    }
}