package com.example.testcharts.mpandroid

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.util.*

// https://malcolmmaima.medium.com/kotlin-implementing-a-barchart-and-piechart-using-mpandroidchart-8c7643c4ba75

class FromMedium {

//    private val MAX_X_VALUE = 13
//    private val GROUPS = 2
//    private val GROUP_1_LABEL = "Orders"
//    private val GROUP_2_LABEL = ""
//    private val BAR_SPACE = 0.1f
//    private val BAR_WIDTH = 0.8f
//    private var chart: LineChart? = null
//    private var pieChart: PieChart? = null      // rename this to lineChart
//    protected var tfRegular: Typeface? = null
//    protected var tfLight: Typeface? = null
//
//    private val statValues: ArrayList<Float> = ArrayList()
//
//    protected val statsTitles = arrayOf(
//        "Orders", "Inventory"
//    )
//
//    private val calendar: Calendar = Calendar.getInstance()
//    private val year = calendar.get(Calendar.YEAR)
//
//
//
//    init {
//        getStats()
//    }
//
//
//    fun getStats() {
//        val values1: ArrayList<BarEntry> = ArrayList()
//        statValues.clear()
//
//        for (i in 0 until MAX_X_VALUE) {
//            values1.add(
//                BarEntry(
//                    i.toFloat(),
//                    (Math.random() * 80).toFloat()
//                )
//            )
//        }
//        displayData(values1)
//    }
//
//    fun displayData(orderData: ArrayList<BarEntry>) {
//        val data: BarData = createChartData(orderData)
//        prepareChartData(data)
//        configureBarChart()
//        preparePieData()
//    }
//
//    // todo use this for line chart INSTEAD of bar chart
//    private fun prepareChartData(data: LineData) {
//        chart!!.data = data
//        chart!!.barData.barWidth = BAR_WIDTH
//
//        val groupSpace = 1f - (BAR_SPACE + BAR_WIDTH) //\* GROUPS  todo: fix this
//        chart!!.groupBars(0f, groupSpace, BAR_SPACE)
//        chart!!.invalidate()
//    }
//
//    private fun configureBarChart() {
//        chart!!.setPinchZoom(false)
//        chart!!.setDrawBarShadow(false)
//        chart!!.setDrawGridBackground(false)
//
//        chart!!.description.isEnabled = false
//        val xAxis = chart!!.xAxis
//        xAxis.granularity = 1f
//        xAxis.setCenterAxisLabels(true)
//        xAxis.setDrawGridLines(false)
//        val leftAxis = chart!!.axisLeft
//        leftAxis.setDrawGridLines(true)
//        leftAxis.spaceTop = 35f
//        leftAxis.axisMinimum = 0f
//        chart!!.axisRight.isEnabled = false
//        chart!!.xAxis.axisMinimum = 1f
//        chart!!.xAxis.axisMaximum = MAX_X_VALUE.toFloat()
//    }
//
//    private fun preparePieData() {
//        // pie chart
//        pieChart!!.setUsePercentValues(true)
//        pieChart!!.description.isEnabled = false
//        pieChart!!.setExtraOffsets(5F, 10F, 5F, 5F)
//
//        pieChart!!.dragDecelerationFrictionCoef = 0.95f
//
//        pieChart!!.setCenterTextTypeface(tfLight)
//        pieChart!!.centerText = "generateCenterSpannableText()"
//
//        pieChart!!.isDrawHoleEnabled = true
//        pieChart!!.setHoleColor(Color.WHITE)
//
//        pieChart!!.setTransparentCircleColor(Color.WHITE)
//        pieChart!!.setTransparentCircleAlpha(110)
//
//        pieChart!!.holeRadius = 58f
//        pieChart!!.transparentCircleRadius = 61f
//
//        pieChart!!.setDrawCenterText(true)
//
//        pieChart!!.rotationAngle = 0.toFloat()
//        // enable rotation of the chart by touch
//        pieChart!!.isRotationEnabled = true
//        pieChart!!.isHighlightPerTapEnabled = true
//
//        pieChart!!.animateY(1400, Easing.EaseInOutQuad)
//        // pieChart.spin(2000, 0, 360);
//
//        pieChart!!.spin(2000, 0F, 360F, Easing.EaseInOutQuad)
//        val l = pieChart!!.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        l.orientation = Legend.LegendOrientation.VERTICAL
//        l.setDrawInside(false)
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 0f
//        l.yOffset = 0f
//
//        // entry label styling
//        pieChart!!.setEntryLabelColor(Color.WHITE)
//        pieChart!!.setEntryLabelTypeface(tfRegular)
//        pieChart!!.setEntryLabelTextSize(12f)
//    }
//
//    fun setPieChartData(orderData: ArrayList<PieEntry>, inventoryData: ArrayList<PieEntry>) {
//        val entries: ArrayList<PieEntry> = ArrayList()
//
//        entries.add(PieEntry(orderData[0].value, "Orders"))
//        entries.add(PieEntry(inventoryData[0].value, "Inventory"))
//
//        //you can test above by adding random dummy data to the pie chart or passing the data from the backend.
//
//        //entries.add(PieEntry(random.nextInt(100).toFloat(), "Orders"))
//        //entries.add(PieEntry(random.nextInt(100).toFloat(), "Inventory"))
//
//        val dataSet = PieDataSet(entries, "")
//        dataSet.setDrawIcons(false)
//        dataSet.sliceSpace = 3f
//        dataSet.iconsOffset = MPPointF(0F, 40F)
//        dataSet.selectionShift = 5f
//
//        // add colors
//        val colors: ArrayList<Int> = ArrayList()
//
//        colors.add(ColorTemplate.rgb( getString(R.color.red.toInt()) ))
//        colors.add(ColorTemplate.rgb( getString(R.color.blue.toInt()) ))
//
//        colors.add(ColorTemplate.getHoloBlue())
//        dataSet.colors = colors
//        //dataSet.setSelectionShift(0f);
//        val data = PieData(dataSet)
//        data.setValueFormatter(PercentFormatter())
//        data.setValueTextSize(11f)
//        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
//        pieChart!!.setData(data)
//
//        // undo all highlights
//        pieChart!!.highlightValues(null)
//        pieChart!!.invalidate()
//    }
}