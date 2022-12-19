package com.example.todayseat.ui.home
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todayseat.SplashActivity


import com.example.todayseat.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import java.lang.Math.abs
import java.text.SimpleDateFormat


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    //HDH db 넣기

    //LSH
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dlg=CustomMenuDialog(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        Log.d("TAG11", "onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //영양분 점수 계산식
        var nST = binding.nScore.text.toString().toInt()
        var totalKcal = binding.kcalChange.text.toString().toInt() // 총섭취칼로리
        var totalCarbo = 0f // 총섭취 탄수화물 (g) 데이터 베이스상 단위 때문에 추가
        var totalFat = 0f // 총섭취 지방 (g) 데이터 베이스상 단위 때문에 추가

        var need_calorie = 0f // 권장 칼로리 : ( 자신의 키 -100 * 0.9 * 활동지수 )
        var kcalS = 0f        // 칼로리점수 :  { |데이터베이스의 합 - ( 자신의 키 -100 * 0.9 * 활동지수 )| - buffer 100kcal  }* 4kcal /(4+9kcal) * 0.25}
        var carboS = 0f       // 탄수화물점수 : {|총칼로리*0.6/4g - 데이터베이스 g|  - buffer 25g }* 4kcal /(4+9kcal) * 0.12
        var fatS = 0f         // 지방점수 : 데이터베이스의 합 - 50g *9kcal /(4+9kcal) * 0.38
        var proteinS = 0f     // 단백질점수 : (영양성분표 기준 권장g 이상 - 데이터베이스의 합g) * 4kcal /(4+9kcal) * 0.25

        //건강 지수 계산

        //need_calorie = height - 100 *0.9 * activation
        kcalS = (abs((totalKcal) - need_calorie) - 100 *4 / (4+9) * 0.25).toFloat()
        carboS = ((abs(totalKcal*0.6/4- totalCarbo) - 25)*4/(4+9)*0.12).toFloat()
        fatS = ((totalFat - 50 ) *9 *(4+9) * 0.38).toFloat()

        binding.recommendText.setOnClickListener {
            loadFragment(HomeFragment2())
        }



        //영양분 점수에 따른 비빔밥 변경 0~20/20~40/40~60/60~80/80~100
        when(nST){
            in 0..20 -> {
                binding.imgBibim0.visibility = View.VISIBLE
                binding.imgBibim1.visibility = View.INVISIBLE
                binding.imgBibim2.visibility = View.INVISIBLE
                binding.imgBibim3.visibility = View.INVISIBLE
                binding.imgBibim4.visibility = View.INVISIBLE
            }
            in 21..40 -> {
                binding.imgBibim0.visibility = View.INVISIBLE
                binding.imgBibim1.visibility = View.VISIBLE
                binding.imgBibim2.visibility = View.INVISIBLE
                binding.imgBibim3.visibility = View.INVISIBLE
                binding.imgBibim4.visibility = View.INVISIBLE
            }
            in 41..60 -> {
                binding.imgBibim0.visibility = View.INVISIBLE
                binding.imgBibim1.visibility = View.INVISIBLE
                binding.imgBibim2.visibility = View.VISIBLE
                binding.imgBibim3.visibility = View.INVISIBLE
                binding.imgBibim4.visibility = View.INVISIBLE
            }
            in 61..80 -> {
                binding.imgBibim0.visibility = View.INVISIBLE
                binding.imgBibim1.visibility = View.INVISIBLE
                binding.imgBibim2.visibility = View.INVISIBLE
                binding.imgBibim3.visibility = View.VISIBLE
                binding.imgBibim4.visibility = View.INVISIBLE
            }
            in 81..100 -> {
                binding.imgBibim0.visibility = View.INVISIBLE
                binding.imgBibim1.visibility = View.INVISIBLE
                binding.imgBibim2.visibility = View.INVISIBLE
                binding.imgBibim3.visibility = View.INVISIBLE
                binding.imgBibim4.visibility = View.VISIBLE
            }
        }


        val currentTime : Long = System.currentTimeMillis()
        val currentYear= SimpleDateFormat("YYYY").format(currentTime)
        val currentMonth= SimpleDateFormat("MM").format(currentTime)
        val currentDay= SimpleDateFormat("dd").format(currentTime)
        val compareDate=currentYear+"-"+currentMonth+"-"+currentDay
        val c = SplashActivity.moappDB.rawQuery("select COUNT(*) from FOODRECENT where Date_eat like '${compareDate}%';",null)
        var count=0
        while(c.moveToNext()){
            count=c.getInt(0)
        }
        Log.d("TAG11",count.toString())
        if (count==0){
            val dlg=CustomMenuDialog(requireActivity())
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }

        val dlg2=CustomMenuScoreDialog(requireActivity())
        dlg2.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dlg2.setCancelable(false)
//
//        //db food table에서 메뉴 가져오기
//        val sql = "SELECT F_name FROM FOOD LIMIT 5"
//        val c: Cursor = moappDB.rawQuery(sql,null)

        var menus= mutableListOf<String>("치킨","제육볶음","오징어볶음","쏘세지야채볶음","돼지고기김치볶음")

//        while (c.moveToNext()){
//            var F_name_pos = c.getColumnIndex("F_name")
//            menus.add(c.getString(F_name_pos))
//        }
//        Log.d("DB1234","menus에 sql로 받아온 것 넣기")
        var menuListAdapter=MenuListAdapter(menus)
        Log.i("DB1234","Food table에서 가져온 데이터 HomeFragment에 넣기")

        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }

        //binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //binding.lifecycleOwner = this
        initBarChart(binding.barChart)
        setData(binding.barChart)

        return root
    }

    // 바 차트 설정
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadFragment(fragment: Fragment) {
        Log.d("clickTest", "click!->" + fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(com.example.todayseat.R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


//    //HDH
//    private fun selectFoodData(db: SQLiteDatabase?){
//        val sql = "SELECT * FROM RECEIPE WHERE R_ID == 100"
//        val c: Cursor = db!!.rawQuery(sql, null)
//
//        while (c.moveToNext()) {
//            val F_ID_pos = c.getColumnIndex("R_ID")
//            val F_name_pos = c.getColumnIndex("R_name")
//            val category_app_pos = c.getColumnIndex("F_ID")
//            val serving_size_pos = c.getColumnIndex("R_ingredient")
//            val content_unit_pos = c.getColumnIndex("R_seasoning")
//            val kcal_pos = c.getColumnIndex("R_step")
//
//            Log.i("DB1234", "get Column in Query ")
//
//            val F_ID = c.getString(F_ID_pos)
//            val F_name = c.getString(F_name_pos)
//            val category_app = c.getString(category_app_pos)
//            val serving_size = c.getString(serving_size_pos)
//            val content_unit = c.getString(content_unit_pos)
//            val kcal = c.getString(kcal_pos)
//
//
//            Log.i("DB1234", "get variance in Column ")
//        }
//    }


}




