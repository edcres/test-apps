package com.example.testroom.basics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroom.R

/**
 * info:
 * - By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the
 *      main thread.
 */

class RoomBasicsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room_basics, container, false)
    }
}