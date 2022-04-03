package com.example.coolfunctions.datastructures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coolfunctions.R

/**
 *
 * Benefits:
 *  - Retrieving elements from anywhere in the list (especially in the middle)
 *
 * Drawbacks:
 * - When an array list adds and new item, it creates a new list and copies all the elements
 * - When deleting an element from the middle, it needs to change every element after the deleted element
 *
 *
 *
 */

class ArrayListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_array_list, container, false)
        return view
    }

    private fun start() {
        val aList: ArrayList<Int> = ArrayList<Int>()
    }
}