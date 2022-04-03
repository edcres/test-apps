package com.example.coolfunctions.datastructures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coolfunctions.R
import java.util.*

/**
 *
 * A tree is similar to a linked list, but a tree can link to multiple other nodes
 *
 * In a tree, there are no two references that link to the same node
 */

class TreeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tree, container, false)
        return view
    }

    private fun start() {
        val treeSet = TreeSet<String>()
        val treeMap = TreeMap<String, String>()
    }
}