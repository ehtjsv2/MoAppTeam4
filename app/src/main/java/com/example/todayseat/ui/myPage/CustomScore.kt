package com.example.todayseat.ui.myPage

import android.database.Cursor
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.ActivityCustomScoreBinding
import com.example.todayseat.databinding.FragmentNutrientScoreBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import java.util.ArrayList

class CustomScore : DialogFragment(), OnChartValueSelectedListener {
    private var _binding: FragmentNutrientScoreBinding? = null
    private val binding get() = _binding!!
    lateinit var chart: LineChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNutrientScoreBinding.inflate(inflater, container, false)
        val view = binding.root

        chart = binding.nSTlinechart
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val xAxis = chart.xAxis
        xAxis.enableGridDashedLine(10f,10f,0f)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setLabelCount(31);
        xAxis.setGranularity(2f);
        xAxis.setGranularityEnabled(true);


        val yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(100f)
        yAxis.setAxisMinimum(0f)

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        //llXAxis.typeface = tfRegular

        val ll1 = LimitLine(150f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        //ll1.typeface = tfRegular

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)

        setData(30, 100f)

        chart.animateX(1500)
        val legend = chart.legend
        legend.form = Legend.LegendForm.NONE
//        chart.getAxisLeft().setDrawLabels(false);
//        chart.getAxisRight().setDrawLabels(false);
//        chart.getXAxis().setDrawLabels(false);

        chart.getLegend().setEnabled(false);   // Hide the legend


        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.setCancelable(false)
        dialog?.window?.setLayout(3000, 3000)
        dialog?.show()
       binding.backBtnToMyPage.setOnClickListener {
           dismiss()    // 대화상자를 닫는 함수
        }

        return view
    }

    private fun setData(count: Int, range: Float) {

        val values = ArrayList<Entry>()

        // check 값을 넣을 수 있는 거 확인
//        val value1 = 50f
//        val value2 = 70f
//        val value3 = 80f
//        values.add(Entry(1f, value1) )
//        values.add(Entry(2f, value2))
//        values.add(Entry(3f, value3))

        //아직 data가 없어서 random 값 입력
        for (i in 1 until count) {
            //value에 지난 영양점수들을 입력하면 됩니다.
            val value = (Math.random() * range).toFloat()
            values.add(Entry(i.toFloat(), value))/*, resources.getDrawable(R.drawable.star)*/
        }

        val set1: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.rgb(10,10,10)
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 2f

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
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

             //set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                //val drawable = ContextCompat.getDrawable(this, R.drawable.bibim4)
                //set1.fillDrawable = drawable
                set1.fillColor = Color.rgb(235,144,150 )
            } else {
                set1.fillColor = Color.rgb(235,144,150 )
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data
        }
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}