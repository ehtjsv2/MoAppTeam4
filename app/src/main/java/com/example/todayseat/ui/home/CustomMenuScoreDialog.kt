package com.example.todayseat.ui.home

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.R
import com.example.todayseat.databinding.InsertMenuDialogBinding
import com.example.todayseat.databinding.ScoreMenuDialogBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


class CustomMenuScoreDialog(var activity: Activity) : Dialog(activity),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding= ScoreMenuDialogBinding.inflate(layoutInflater)
        // 확인 버튼클릭시, 무시
        binding.ignoreBtn.setOnClickListener {
            dismiss()
        }
        initBarChart(binding.insertMenuRecyclerView)
        setData(binding.insertMenuRecyclerView)
        Log.d("TAG11","다이얼로그출력!")
        setContentView(binding.root)

//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        //TODO("Not yet implemented")
    }

}
private fun initBarChart(barChart: BarChart) {
    // 차트 회색 배경 설정 (default = false)
    barChart.setDrawGridBackground(false)
    // 막대 그림자 설정 (default = false)
    barChart.setDrawBarShadow(false)
    // 차트 테두리 설정 (default = false)
    barChart.setDrawBorders(false)

    val description = Description()
    // 오른쪽 하단 모서리 설명 레이블 텍스트 표시 (default = false)
    description.isEnabled = false
    barChart.description = description

    // X, Y 바의 애니메이션 효과
    barChart.animateY(1000)
    barChart.animateX(1000)

    // 바텀 좌표 값
    val xAxis: XAxis = barChart.xAxis
    // x축 위치 설정
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    // 그리드 선 수평 거리 설정
    xAxis.granularity = 1f
    // x축 텍스트 컬러 설정
    xAxis.textColor = Color.BLACK
    // x축 선 설정 (default = true)
    xAxis.setDrawAxisLine(false)
    // 격자선 설정 (default = true)
    xAxis.setDrawGridLines(false)

    val leftAxis: YAxis = barChart.axisLeft
    // 좌측 선 설정 (default = true)
    leftAxis.setDrawAxisLine(false)
    // 좌측 텍스트 컬러 설정
    leftAxis.textColor = Color.BLACK

    val rightAxis: YAxis = barChart.axisRight
    // 우측 선 설정 (default = true)
    rightAxis.setDrawAxisLine(false)
    // 우측 텍스트 컬러 설정
    rightAxis.textColor = Color.GREEN

    // 바차트의 타이틀
    val legend: Legend = barChart.legend
    // 범례 모양 설정 (default = 정사각형)
    legend.form = Legend.LegendForm.LINE
    // 타이틀 텍스트 사이즈 설정
    legend.textSize = 20f
    // 타이틀 텍스트 컬러 설정
    legend.textColor = Color.BLACK
    // 범례 위치 설정
    legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
    // 범례 방향 설정
    legend.orientation = Legend.LegendOrientation.HORIZONTAL
    // 차트 내부 범례 위치하게 함 (default = false)
    legend.setDrawInside(false)
}

// 차트 데이터 설정
private fun setData(barChart: BarChart) {

    // Zoom In / Out 가능 여부 설정
    barChart.setScaleEnabled(false)

    val valueList = ArrayList<BarEntry>()
    val title = " "

    // 임의 데이터
    /*for (i in 0 until 3) {
        valueList.add(BarEntry(i.toFloat(), i * 100f))
    }
     */

    // 여기를 100점 만점으로 기준 잡아야할듯 탄단지가 서로 단위가 너무 다름
    valueList.add(BarEntry(1f, 90f)) // 가장 아래에 들어감 지방
    valueList.add(BarEntry(2f, 65f)) // 단백질
    valueList.add(BarEntry(3f, 20f)) // 탄수화물

    val barDataSet = BarDataSet(valueList, title)
    // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
    // 33(빨간색)이수현 66(주황색) 100(초록색) if문으로 구현예정
    //아래에서부터 들어감
    barDataSet.setColors(
        Color.rgb(191,225,192), Color.rgb(253,208,89),
        Color.rgb(223,88,66))

    val data = BarData(barDataSet)
    barChart.data = data
    barChart.invalidate()
}

