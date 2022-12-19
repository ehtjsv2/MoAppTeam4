package com.example.todayseat.ui.home


import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todayseat.R
import com.example.todayseat.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todayseat.MainActivity
import com.example.todayseat.SplashActivity
import com.example.todayseat.SplashActivity.Companion.moappDB

//import com.example.todayseat.MainActivity.Companion.moappDB



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

//        val helper = myDBHelper1(this)
//        val moappDB = helper.writableDatabase
//        helper.onUpgrade(moappDB, 1, 2)
//        MainActivity.helper
//        MainActivity.moappDB

        dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dlg.setCancelable(false)
        dlg.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        Log.d("TAG11","onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        db food table에서 메뉴 가져오기
//        val sql = "SELECT * FROM FOOD LIMIT 2 "
//        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
//        Log.d("DB1234","HomeFragment cursor")

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
        transaction.replace(R.id.fragment_container, fragment)
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




