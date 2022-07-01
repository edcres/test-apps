package com.example.testcharts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

class MPAndroidChartFragment : Fragment() {

    private lateinit var lineChartBtn: Button
    private lateinit var pieChartBtn: Button
    private lateinit var lineContainer: LinearLayout
    private lateinit var pieContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_m_p_android_chart, container, false)

        lineChartBtn = view.findViewById(R.id.line_chart_btn)
        pieChartBtn = view.findViewById(R.id.pie_chart_btn)
        lineContainer = view.findViewById(R.id.line_container)
        pieContainer = view.findViewById(R.id.pie_container)

        lineChartBtn.setOnClickListener {
            pieContainer.visibility = View.GONE
            lineContainer.visibility = View.VISIBLE
        }
        pieChartBtn.setOnClickListener {
            lineContainer.visibility = View.GONE
            pieContainer.visibility = View.VISIBLE
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}