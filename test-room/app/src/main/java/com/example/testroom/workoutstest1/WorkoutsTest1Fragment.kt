package com.example.testroom.workoutstest1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testroom.R
import com.example.testroom.databinding.FragmentWorkoutsTest1Binding

class WorkoutsTest1Fragment : Fragment() {

    private var binding: FragmentWorkoutsTest1Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentWorkoutsTest1Binding
            .inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            groupEt
            workoutTitleEt
            actionBtn
            repsTxt1
            weightTxt1
        }
    }
}