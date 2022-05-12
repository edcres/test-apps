package com.example.testrecyclerview.listadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecyclerview.R
import com.example.testrecyclerview.listadapter.StartRecyclerAdapter
import java.util.*

private const val TAG = "LAFrag_TAG"

class LAFragment : Fragment() {

    private lateinit var refreshBtn: Button
    private lateinit var addBtn: Button
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

        // Start animation
        refreshBtn = view.findViewById(R.id.refresh_btn)
        addBtn = view.findViewById(R.id.add_btn)
        startRecyclerview = view.findViewById(R.id.start_recyclerview)
        startRecyclerview.layoutAnimation = animController
        startRecyclerview.adapter = recyclerAdapter
        startRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        // Swipe to delete
        val editItemCallback = object : ItemMoveCallback(
            ContextCompat.getColor(requireContext(), R.color.delete_color),
            R.drawable.ic_delete_24
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                Collections.swap(viewModel.basicItems.value!!, fromPosition, toPosition)
                recyclerAdapter.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition // position of the item in the UI
                viewModel.removeItemAt(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(editItemCallback)
        itemTouchHelper.attachToRecyclerView(startRecyclerview)

        viewModel.basicItems.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it.toList())
        }

        addBtn.setOnClickListener {
            viewModel.addItem()
        }

        refreshBtn.setOnClickListener {
            startRecyclerview.startLayoutAnimation()
        }

        return view
    }
}