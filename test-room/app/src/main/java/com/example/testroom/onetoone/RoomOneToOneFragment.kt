package com.example.testroom.onetoone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroom.R

class RoomOneToOneFragment : Fragment() {

    private val fragmentTAG = "OneToOneFragTAG"
    private lateinit var viewModelFactory: OneToOneViewModelFactory
    private lateinit var viewModel: OneToOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_one_to_one, container, false)
        return view
    }
}