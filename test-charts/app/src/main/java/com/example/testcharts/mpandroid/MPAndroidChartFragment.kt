package com.example.testcharts.mpandroid

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testcharts.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils

class MPAndroidChartFragment : Fragment(), OnChartValueSelectedListener {

    private lateinit var lineChartBtn: Button
    private lateinit var pieChartBtn: Button
    private lateinit var lineContainer: LinearLayout
    private lateinit var pieContainer: LinearLayout
    private lateinit var lineChart: LineChart
    private lateinit var pieChart: PieChart

    private lateinit var xAxis: XAxis
    private lateinit var yAxis: YAxis
    private lateinit var llXAxis: LimitLine
    private lateinit var ll1: LimitLine
    private lateinit var ll2: LimitLine

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_m_p_android_chart, container, false)

        lineChartBtn = view.findViewById(R.id.line_chart_btn)
        pieChartBtn = view.findViewById(R.id.pie_chart_btn)
        lineContainer = view.findViewById(R.id.line_container)
        pieContainer = view.findViewById(R.id.pie_container)
        lineChart = view.findViewById(R.id.line_chart_view)
        pieChart = view.findViewById(R.id.pie_chart_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChartBtn.setOnClickListener {
            pieContainer.visibility = View.GONE
            lineContainer.visibility = View.VISIBLE
        }
        pieChartBtn.setOnClickListener {
            lineContainer.visibility = View.GONE
            pieContainer.visibility = View.VISIBLE
        }

        makeLineChart()
        makePieChart()
    }

    // the 2 below are for OnChartValueSelectedListener
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        // todo: test this
        Log.i("Entry selected", e.toString())
        Log.i("LOW HIGH", "low: " + lineChart.lowestVisibleX + ", high: " + lineChart.highestVisibleX)
        Log.i("MIN MAX", "xMin: " + lineChart.xChartMin + ", xMax: " + lineChart.xChartMax + ", yMin: " + lineChart.yChartMin + ", yMax: " + lineChart.yChartMax)
    }
    override fun onNothingSelected() {
        // todo: test this
        Log.i("Nothing selected", "Nothing selected.")
    }

    private fun makeLineChart() {
        lineChart.setBackgroundColor(resources.getColor(R.color.white))
//        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)

        lineChart.setOnChartValueSelectedListener(this)
        lineChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        // Set the marker to the chart
        mv.chartView = lineChart
        lineChart.marker = mv

        // enable scaling and dragging
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        lineChart.setPinchZoom(true)

        xAxis = lineChart.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        yAxis = lineChart.axisLeft
        // disable dual axis (only use LEFT axis)
        lineChart.axisRight.isEnabled = false
        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        // axis range
        yAxis.axisMaximum = 200f
        yAxis.axisMinimum = -50f

        // Create Limit Lines
        llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
//        llXAxis.typeface = tfRegular
        ll1 = LimitLine(150f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
//        ll1.typeface = tfRegular
        ll2 = LimitLine(-30f, "Lower Limit")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
//        ll2.typeface = tfRegular
        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)
        // add limit lines
        yAxis.addLimitLine(ll1)
        yAxis.addLimitLine(ll2)
        //xAxis.addLimitLine(llXAxis);

        // add data
        setLinearData(45, 180f)
        // draw points over time
        lineChart.animateX(1500)
        // get the legend (only possible after setting data)
        val l: Legend = lineChart.legend
        // draw legend entries as lines
        l.form = LegendForm.LINE
    }

    private fun makePieChart() {
    }

    // Generate dummy values for the data
    fun setLinearData(count: Int, range: Float) {

        var set1: LineDataSet?
        val values = ArrayList<Entry>()

        for (i in 0 until count) {
            val value = (Math.random() * range).toFloat() - 30
            values.add(Entry(i.toFloat(), value, resources.getDrawable(R.drawable.star)))
//            values.add(Entry(i, value, ResourcesCompat.getDrawable(R.drawable.star)))
        }

        if (lineChart.data != null && lineChart.data.dataSetCount > 0) {
            // If data has already been created.
            set1 = lineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            lineChart.data.notifyDataChanged()
            lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)
            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)
            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)
            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            // draw points as solid circles
            set1.setDrawCircleHole(false)
            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            // text size of values
            set1.valueTextSize = 9f
            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> lineChart.axisLeft.axisMinimum }
            // set color of filled area
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets
            // create a data object with the data sets
            val data = LineData(dataSets)
            // set data
            lineChart.data = data
        }
    }

    fun setPieData() {
    }
}

















//