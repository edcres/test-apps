package com.example.coolfunctions.datastructures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coolfunctions.R

/**
 * HashMap vs HashTable
 *
 * Benefits:
 *  - Allows for the very fast retrieval of data no matter how much there is
 *
 *
 */


class HashMapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hash_map, container, false)
    }

    private fun start() {
        val hMap: HashMap


        val startTime = System.nanoTime()
        val endTime = System.nanoTime()
        val totalTimerTime = endTime - startTime
    }
}