package com.example.coolfunctions.datastructures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coolfunctions.R

// https://medium.com/huawei-developers/most-used-data-structures-in-kotlin-clash-of-data-structures-3f2746aafee


/**
 * HashMap vs HashTable
 *
 * Benefits:
 *  - Allows for the very fast retrieval of data no matter how much there is
 *
 * Insertion/LookUp  O(1)
 */


class HashMapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hash_map, container, false)
    }

    private fun startHMap() {
        val hMap = HashMap<Int, String>()
        hMap.put(1, "Hussein")
        hMap.put(2, "Jenna")
        hMap.put(3, "Laya")
        hMap.get(3)

        hMap.forEach { (key, value) ->

        }


        val startTime = System.nanoTime()
        val endTime = System.nanoTime()
        val totalTimerTime = endTime - startTime


//        hMap.merge(it, 1, Integer::sum)
    }

    private fun startLHMap() {
        val hMap = LinkedHashMap<Int, String>()
    }
}