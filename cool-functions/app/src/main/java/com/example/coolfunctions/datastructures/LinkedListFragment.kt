package com.example.coolfunctions.datastructures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coolfunctions.R
import java.util.*

/**
 * Move this note to the O(n) fragment
 *
 * With data structures, we care about how they perform as the data scales
 */

/**
 * Notes:
 *
 * Items point to the next element (not sequential, it just points to the next element)
 * - An array has a fixed size but a linkedList doesn't have a size it just points to the next element
 *
 * The data inside the linkedList could be a node class
 *
 * Major drawback:
 * - to get an item in the list, you have to go through each item until you get to that one
 *      - O(n)
 *
 * Major benefit:
 * - inserting/deleting an item in the middle of the list
 *      - you only have to get the item before and point it to it, and it pointing to the item after
 *      - if the location of the item before is known, you don't have to iterate through the other items to find it.
 *      - in an array you would insert an item and move every item after up one index
 * - inserting at the beginning of the list can be very quick.   constant time O(1)
 *
 * Doubly-Linked List
 *  - An implementation of the linked list that has a reference to both directions:
 *      - The previous item and the next item.
 */

class LinkedListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_linked_list, container, false)
        return view
    }

    private fun start() {
        val lList: LinkedList<Int> = LinkedList()
        lList.add(5)
        lList.addFirst(2)
        lList.remove()
    }
}