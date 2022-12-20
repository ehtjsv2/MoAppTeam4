package com.example.todayseat.ui.home

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