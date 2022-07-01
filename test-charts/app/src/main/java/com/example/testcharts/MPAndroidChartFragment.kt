package com.example.testcharts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MPAndroidChartFragment : Fragment() {

    private lateinit var lineGraphBtn: Button
    private lateinit var pieChartBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_m_p_android_chart, container, false)

        lineGraphBtn = view.findViewById(R.id.line_graph_btn)
        pieChartBtn = view.findViewById(R.id.pie_chart_btn)

        lineGraphBtn.setOnClickListener {

        }
        pieChartBtn.setOnClickListener {

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}