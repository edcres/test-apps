package com.example.testcharts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class StartFragment : Fragment() {

    private lateinit var helloChartsBtn: Button
    private lateinit var mpAndroidChartBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        helloChartsBtn = view.findViewById(R.id.hello_charts_btn)
        mpAndroidChartBtn = view.findViewById(R.id.mp_android_chart_btn)
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        helloChartsBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_helloChartsFragment)
        }
        mpAndroidChartBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_MPAndroidChartFragment)
        }
        return view
    }
}