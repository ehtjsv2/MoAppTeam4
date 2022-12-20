package com.example.todayseat.ui.home
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
import com.example.todayseat.SplashActivity
import com.example.todayseat.SplashActivity.Companion.moappDB


import com.example.todayseat.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.selects.select
import java.lang.Math.abs
import java.text.SimpleDateFormat

import kotlin.properties.Delegates


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    //HDH db 넣기

    //LSH
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //bar chart 연결을 위한 global 변수 감점요소입니다!!!!!! 크면 클수록 안 좋은 거에요!!!!
    var kcalS = 0f        // 칼로리점수 :  { |데이터베이스의 합 - ( 자신의 키 -100 * 0.9 * 활동지수 )| - buffer 100kcal  }* 4kcal /(4+9kcal) * 0.25}
    var carboS = 0f       // 탄수화물점수 : {|총칼로리*0.6/4g - 데이터베이스 g|  - buffer 25g }* 4kcal /(4+9kcal) * 0.12
    var fatS = 0f         // 지방점수 : 데이터베이스의 합 - 50g *9kcal /(4+9kcal) * 0.38
    var proteinS = 0f     // 단백질점수 : (영양성분표 기준 권장g 이상 - 데이터베이스의 합g) * 4kcal /(4+9kcal) * 0.25

    // 여기가 100점으로 점수화한 데이터 입니다!!! 높으면 높을 수록 좋은겁니다!!!
    var fatS100 = 0f
    var proteinS100 = 0f
    var carboS100 =0f
    var kcalS100 =0f
    // 위 데이터는 임의로 불러온 점수입니다. xml 확인용입니다.

    //chart를 위한 초기화
    var color1 : Int = 0
    var color2 : Int = 0
    var color3 : Int = 0

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
        val currentTime : Long = System.currentTimeMillis()
        val currentYear= SimpleDateFormat("YYYY").format(currentTime)
        val currentMonth= SimpleDateFormat("MM").format(currentTime)
        val currentDay= SimpleDateFormat("dd").format(currentTime)
        val currentHH= SimpleDateFormat("HH").format(currentTime).toInt()
        val compareDate=currentYear+"-"+currentMonth+"-"+currentDay
        val c = SplashActivity.moappDB.rawQuery("select Date_eat,food_eat_ID from FOODRECENT where Date_eat like '${compareDate}%';",null)
        lateinit var date:String
        var date2:Long=0
        var count:Int=0
        var m_H=-1//아침시간
        var l_H=-1//점심시간
        var d_H=-1//저녁시간
        var m_menu:String?=null
        var l_menu:String?=null
        var d_menu:String?=null
        var menu:String
        while(c.moveToNext()){
            count++
            date=c.getString(0)
            date2=c.getLong(0)
            Log.d("TAG11","current menu date= $date, Long= $date2")
            var ar=date.split(" ") // [i]의 시간이 담김
            var time=ar[1]
            var HH=time.split(":")
            if(HH[0].toInt()<12 && HH[0].toInt()>=6){ // 아침
                m_H=HH[0].toInt()
                m_menu=c.getString(1)
            }
            else if(HH[0].toInt()<18 && HH[0].toInt()>=12){ // 점심
                l_H=HH[0].toInt()
                l_menu=c.getString(1)
            }
            else if(currentHH<24 || currentHH<6){ // 저녁
                d_H=HH[0].toInt()
                d_menu=c.getString(1)
            }
            val currentHH=SimpleDateFormat("HH").format(date2)
            Log.d("TAG11","$m_H, $l_H, $d_H")
            if(currentHH.toInt()<12 && currentHH.toInt()>=6){
                m_H=currentHH.toInt()

            }
            else if(currentHH.toInt()<18){
                l_H=currentHH.toInt()
            }
            else if(currentHH.toInt()<24 || currentHH.toInt()<6){
                d_H=currentHH.toInt()
            }
        }
        if(count==0){
            val dlg=CustomMenuDialog(requireActivity())
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }
        else{
            val ar=date.split(" ")
            val time=ar[1]
            val HH=time.split(":")
            val dlg=CustomMenuDialog(requireActivity())
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)

            if(currentHH.toInt()<12 && currentHH>=6){
                if(m_H!=-1){

                }else{
                    dlg.show()
                }
            }
            else if(currentHH<18){
                if(l_H!=-1){

                }
                else{
                    dlg.show()
                }
            }
            else if(currentHH<24 || currentHH<6){
                if(d_H!=-1){
                    Log.d("TAG11","current= $currentHH, HH= ${HH[0]}")
                }
                else{
                    Log.d("TAG11","current= $currentHH, d_H= $d_H")
                    dlg.show()
                }
            }
            //Log.d("TAG11",time.toString())
        }
        //아침
        var m_kcal:Float=0f
        var m_carbo:Float=0f
        var m_fat:Float=0f
        var m_pro:Float=0f
        //점심
        var l_kcal:Float=0f
        var l_carbo:Float=0f
        var l_fat:Float=0f
        var l_pro:Float=0f
        //저녁
        var d_kcal:Float=0f
        var d_carbo:Float=0f
        var d_fat:Float=0f
        var d_pro:Float=0f
        val c2 :Cursor
        val c3 :Cursor
        val c4 :Cursor
        if(m_menu!=null){
            c2 = SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$m_menu';",null)
            c2.moveToNext()
            m_kcal=c2.getFloat(0)
            m_carbo=c2.getFloat(1)
            m_fat=c2.getFloat(2)
            m_pro=c2.getFloat(3)
        }
        if(l_menu!=null){
            c3=SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$l_menu';",null)
            c3.moveToNext()
            l_kcal=c3.getFloat(0)
            l_carbo=c3.getFloat(1)
            l_fat=c3.getFloat(2)
            l_pro=c3.getFloat(3)
        }
        if(d_menu!=null){
            c4=SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$d_menu';",null)
            c4.moveToNext()
            d_kcal=c4.getFloat(0)
            d_carbo=c4.getFloat(1)
            d_fat=c4.getFloat(2)
            d_pro=c4.getFloat(3)
        }
        Log.d("TAG11","아침=${m_kcal}, ${m_carbo}, ${m_fat}, ${m_pro}")
        Log.d("TAG11","점심=${l_kcal}, ${l_carbo}, ${l_fat}, ${l_pro}")
        Log.d("TAG11","저녁=${d_kcal}, ${d_carbo}, ${d_fat}, ${d_pro}")
        val total_kcal=m_kcal+l_kcal+d_kcal
        val total_carbo=m_carbo+l_carbo+d_carbo
        val total_fat=m_fat+l_fat+d_fat
        val total_pro=m_pro+l_pro+d_pro
        Log.d("TAG11","총(탄,지,단)= ${total_kcal}. ${total_carbo}, ${total_fat}, ${total_pro}")


        //영양분 점수 계산식
        // #####################여기를 바꾸시면 적용됩니다
        var nST = 0f //영양분 점수
        var totalKcal = total_kcal // 총섭취칼로리
        // #####################여기를 바꾸시면 적용됩니다

        //고객 정보
        var height = 175f
        var activation = 30f

        //DB 에서 합한 합
        var totalCarbo = total_carbo // 총섭취 탄수화물 (g) 데이터 베이스상 단위 때문에 추가
        var totalFat = total_fat // 총섭취 지방 (g) 데이터 베이스상 단위 때문에 추가
        var totalProtein = total_pro   // 총섭취 단백질 (g) 데이터 베이스상 단위 때문에 추가

        var need_protein = 50f // 권장 단백질
        var need_calorie = 0f // 권장 칼로리 : ( 자신의 키 -100 * 0.9 * 활동지수 )
        var need_fat=0f
        var need_carbo=50f

        //건강 지수 계산
        need_calorie =
            ((height - 100f) *0.9f * activation) // 권장 칼로리 (자신의 키 - 100) *0.9 * 활동지수
        need_carbo=need_calorie*0.6f
        var kcals_minus=((kotlin.math.abs((totalKcal) - need_calorie)-300f))
        if(kcals_minus<0){
            kcals_minus=0f
        }
        kcalS = (100f*0.25f - (kcals_minus)/(4f+9f) * 0.25f)
        if(kcalS<0)kcalS=0f
        var carbos_minus=(kotlin.math.abs(totalKcal * 0.6f / 4f - totalCarbo)-25f)
        if(carbos_minus<0){
            carbos_minus=0f
        }
        carboS = (100f*0.12f - (carbos_minus)*4f/(6f)*0.12f)
        if(carboS<0)carboS=0f
        fatS = (100f*0.38f - ((totalFat - 50f ) *9f /(4f+9f) * 0.38f))
        if(fatS<0)fatS=0f
        proteinS = 25f*(total_pro/need_protein)
        if(proteinS>=25)proteinS=25f
        Log.d("TAG11","칼,탄,지,단 = ${kcalS}, ${carboS}, ${fatS}, ${proteinS}")
        nST = kcalS+carboS+fatS+proteinS // 최종 영양분 점수 nutrientScoreTotal
        //if(nST<0)nST=0f
        //그래프를 위한 백점 환산
        if(total_fat==0f){
            fatS100=0f
        }
        else{
            fatS100 = (fatS.toDouble() * 100.0 / 38.0).toFloat()
            if (fatS100>=100) fatS100=100f
        }

        //proteinS100 = (proteinS.toDouble()*100.0/25.0).toFloat()
        if(need_protein-totalProtein<0){
            proteinS100=100f
        }
        else{
            proteinS100 = 100-((need_protein-totalProtein)/need_protein*100).toFloat()
        }

        //if (proteinS100>=100) proteinS100=100f
        if(total_carbo==0f){
            carboS100=0f
        }
        carboS100 = (carboS.toDouble()*100.0/25.0).toFloat()
        if (carboS>=100) carboS=100f

        //점수 반영
        binding.nScore.setText(nST.toInt().toString())
        binding.kcalChange.setText(totalKcal.toInt().toString())

        binding.recommendText.setOnClickListener {
            loadFragment(HomeFragment2())
        }

        //영양분 점수에 따른 비빔밥 변경 0~20/20~40/40~60/60~80/80~100
        when(nST.toInt()){
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



//        if (count==0){
//
//        }

//        val dlg2=CustomMenuScoreDialog(requireActivity())
//        dlg2.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//        dlg2.setCancelable(false)
//
//        //db food table에서 메뉴 가져오기, 한개만 가져와서 test해보기
//        val sql = "SELECT * FROM FOOD LIMIT 2"
//        val cul: Cursor = moappDB.rawQuery(sql,null)

        var menus= mutableListOf<String>("치킨","제육볶음","오징어볶음","쏘세지야채볶음","돼지고기김치볶음")

//        while (cul.moveToNext()){
//            var F_name_pos = cul.getColumnIndex("preference_check")
//            menus.add(cul.getString(F_name_pos))
//        }
//        Log.d("DB1234", menus.toList().toString())
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
        xAxis.textColor = Color.rgb(10,10,10)
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(true)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)
        // 라벨 표시여부
        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = MyAxisFormatter()

        val leftAxis: YAxis = barChart.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
        // 좌측 텍스트 컬러 설정
        leftAxis.textColor = Color.rgb(186,176,164)
        leftAxis.setAxisMaximum(100f)
        leftAxis.setAxisMinimum(0f)


        val rightAxis: YAxis = barChart.axisRight
        // 우측 선 설정 (default = true) 선 지움
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawGridLines(false)
        // 우측 텍스트 컬러 설정 (흰색으로 안 보이게)
        rightAxis.textColor = Color.rgb(255,255,255)


        //default라 3개의 네모로 표현됩니다.
        /*
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
        legend.setDrawInside(true)
        */
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
        valueList.add(BarEntry(1f, fatS100)) // 가장 아래에 들어감 지방
        valueList.add(BarEntry(2f, proteinS100)) // 단백질
        valueList.add(BarEntry(3f, carboS100)) // 탄수화물

        val barDataSet = BarDataSet(valueList, title)
        // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
        // 33(빨간색)이수현 66(주황색) 100(초록색)

        //var color1 by Delegates.notNull<Int>()
        //var color2 by Delegates.notNull<Int>()
        //var color3 by Delegates.notNull<Int>()

        when(carboS100.toInt()){
            in 0..33 ->
                color1 = Color.rgb(223,88,66)
            in 34..66 ->
                color1 = Color.rgb(253,208,89)
            in 67..100 ->
                color1 = Color.rgb(191,225,192)
        }

        when(proteinS100.toInt()){
            in 0..33 ->  color2 = Color.rgb(223,88,66)
            in 34..66 -> color2 = Color.rgb(253,208,89)
            in 67..100 ->  color2 = Color.rgb(191,225,192)
        }

        when(fatS100.toInt()){
            in 0..33 ->  color3 = Color.rgb(223,88,66)
            in 34..66 -> color3 = Color.rgb(253,208,89)
            in 67..100 ->  color3 = Color.rgb(191,225,192)
        }
        //아래에서부터 들어감
        barDataSet.setColors(
            color3,color2,color1)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
    }

    inner class MyAxisFormatter : ValueFormatter(){
        private val nutrientList = arrayOf("지방","단백질","탄수화물")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return nutrientList.getOrNull(value.toInt()-1)?: value.toString()
        }
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




