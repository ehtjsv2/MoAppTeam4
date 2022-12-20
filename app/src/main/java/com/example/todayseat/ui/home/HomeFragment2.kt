package com.example.todayseat.ui.home

import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.FragmentHome3Binding
import java.text.SimpleDateFormat
import java.util.Random


private var _binding: FragmentHome3Binding? = null
private val binding get() = _binding!!
class HomeFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentHome3Binding.inflate(inflater, container, false)
        //요리하기버튼
        val currentTime : Long = System.currentTimeMillis()
        val currentYear= SimpleDateFormat("YYYY").format(currentTime)
        val currentMonth= SimpleDateFormat("MM").format(currentTime)
        val currentDay= SimpleDateFormat("dd").format(currentTime)
        val currentHH= SimpleDateFormat("HH").format(currentTime).toInt()
        val compareDate=currentYear+"-"+currentMonth+"-"+currentDay
        val c10 = SplashActivity.moappDB.rawQuery("select Date_eat,food_eat_ID from FOODRECENT where Date_eat like '${compareDate}%';",null)
        lateinit var date:String
        var date2:Long=0
        var count1:Int=0
        var m_H=-1//아침시간
        var l_H=-1//점심시간
        var d_H=-1//저녁시간
        var m_menu:String?=null
        var l_menu:String?=null
        var d_menu:String?=null
        var menu:String
        while(c10.moveToNext()){
            count1++
            date=c10.getString(0)
            date2=c10.getLong(0)
            Log.d("TAG11","current menu date= $date, Long= $date2")
            var ar=date.split(" ") // [i]의 시간이 담김
            var time=ar[1]
            var HH=time.split(":")
            if(HH[0].toInt()<12 && HH[0].toInt()>=6){ // 아침
                m_H=HH[0].toInt()
                m_menu=c10.getString(1)
            }
            else if(HH[0].toInt()<18 && HH[0].toInt()>=12){ // 점심
                l_H=HH[0].toInt()
                l_menu=c10.getString(1)
            }
            else if(currentHH<24 || currentHH<6){ // 저녁
                d_H=HH[0].toInt()
                d_menu=c10.getString(1)
            }
            val currentHH= SimpleDateFormat("HH").format(date2)
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
        if(count1==0){
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
        val c2 : Cursor
        val c5 : Cursor
        val c4 : Cursor
        if(m_menu!=null){
            c2 = SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$m_menu';",null)
            c2.moveToNext()
            m_kcal=c2.getFloat(0)
            m_carbo=c2.getFloat(1)
            m_fat=c2.getFloat(2)
            m_pro=c2.getFloat(3)
        }
        if(l_menu!=null){
            c5=SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$l_menu';",null)
            c5.moveToNext()
            l_kcal=c5.getFloat(0)
            l_carbo=c5.getFloat(1)
            l_fat=c5.getFloat(2)
            l_pro=c5.getFloat(3)
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
//        음식군 선택 기준: 선호점수 상위 4개 중 -> 랜덤 1개 선택
//
//        음식군 메뉴를 가져와서 -> 음식군 안에서 랜덤으로 10개를 선정
//        부족 영양분 기준으로 1등은 0~49, 2등은 50~79, 3등은 80~99으로 랜덤으로 가중치를 두어서
//        (50, 30, 20퍼의 비중으로 두어서 사실상 랜덤은 아니게 된다)

        var indexlist:List<Int> = listOf(1,2,3,4,5,6,7)
        var valuelist:MutableList<Int> = ArrayList()
        var myMap:MutableMap<Int,Int> = mutableMapOf(0 to 0)
        var ranklist:MutableList<Int> = ArrayList()
        var allfood:MutableList<String> = ArrayList()
        var selectfood:MutableList<String> = ArrayList()

        val sql = "SELECT meat, seafood, vegetable, noodle, snack_bar, korean, rice, etc FROM foodfavor where Favor_ID=1"
        val c = SplashActivity.moappDB.rawQuery(sql,null)
        c.moveToNext()
        var check_value: Int = c.getColumnIndex("meat")
        var str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("seafood")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("vegetable")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("noodle")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("snack_bar")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("korean")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("rice")
        str = c.getInt(check_value)
        valuelist.add(str)
        check_value = c.getColumnIndex("etc")
        str = c.getInt(check_value)
        valuelist.add(str)

        myMap.clear()
        for(i in 0..indexlist.size-1){
            myMap.put(indexlist[i],valuelist[i])
        }

        myMap = myMap.toList().sortedByDescending { it.second }.toMap() as MutableMap
        var count = 0
        for((key,value) in myMap ){
            if(count == 4)
                break;
            ranklist.add(key)
            count = count + 1
        }
        val random = Random()
        var num = random.nextInt(4)

        var selectN = ranklist[num]

        val sql3 = "SELECT * FROM FOOD where category_app = ${selectN}"
        val c3 = SplashActivity.moappDB.rawQuery(sql3,null)
        while (c3.moveToNext()){
            var F_name_pos = c3.getColumnIndex("F_ID")
            allfood.add(c3.getString(F_name_pos))
        }
        for(i in 0 ..9) {
            num = random.nextInt(allfood.size)
            selectfood.add(allfood[num])
            allfood.removeAt(num)
        }
        //등수정하기 후에

        num = random.nextInt(100)
        if(num<=49){
            Log.d("pjy",selectfood[0])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood[0]}'"
            val c4 = SplashActivity.moappDB.rawQuery(sql4,null)
            c4.moveToNext()
            var F_name_pos = c4.getColumnIndex("F_name")
            var fname = c4.getString(F_name_pos)
            Log.d("pjy",fname)
            binding.home2MenuName.text = fname
            food_name = fname
        }
        else if(num <= 79){
            Log.d("pjy",selectfood[1])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood[1]}'"
            val c4 = SplashActivity.moappDB.rawQuery(sql4,null)
            c4.moveToNext()
            var F_name_pos = c4.getColumnIndex("F_name")
            var fname = c4.getString(F_name_pos)
            Log.d("pjy",fname)
            binding.home2MenuName.text = fname
            food_name = fname
        }
        else{
            Log.d("pjy",selectfood[2])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood[2]}'"
            val c4 = SplashActivity.moappDB.rawQuery(sql4,null)
            c4.moveToNext()
            var F_name_pos = c4.getColumnIndex("F_name")
            var fname = c4.getString(F_name_pos)
            Log.d("pjy",fname)
            binding.home2MenuName.text = fname
            food_name = fname
        }


        binding.reload.setOnClickListener {
            loadFragment(HomeFragment2())
        }
        binding.cook.setOnClickListener {
            loadFragment(RecipeFragment())
        }
        binding.nScoreNext.setOnClickListener {
            val dlg2=CustomMenuScoreDialog(requireActivity())
            dlg2.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg2.setCancelable(false)
            dlg2.show()
        }
        //좋아요 버튼 눌리면
        binding.likeFood.setOnClickListener{

        }
        //싫어요 버튼 눌리면
        binding.dislikeFood.setOnClickListener {

        }
        return binding.root
    }
    private fun loadFragment(fragment: Fragment) {
        Log.d("clickTest", "click!->" + fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //test test
    }
    companion object{
        var food_name="우동(중식)"
    }
}