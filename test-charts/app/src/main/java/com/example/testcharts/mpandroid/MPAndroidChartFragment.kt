package com.example.testcharts.mpandroid

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testcharts.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

// dummy data
private val parties = arrayOf(
    "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
    "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
    "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
    "Party Y", "Party Z"
)

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
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = true
        pieChart.setExtraOffsets(5f,10f,5f,5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
//        pieChart.setCenterTextTypeface(tfLight)
        pieChart.centerText = generateCenterSpannableText()

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f

        pieChart.setDrawCenterText(true)
        pieChart.rotationAngle = 0f
        // enable rotation of the chart by touch
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE)
//        pieChart.setEntryLabelTypeface(tfRegular)
        pieChart.setEntryLabelTextSize(12f)

        setPieData(6, 14f)
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

    fun setPieData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    parties[i % parties.size],
                    resources.getDrawable(R.drawable.star)
                )
            )
        }

        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = java.util.ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }

    // HELPERS //
    // for pie chart
    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }
}

















//