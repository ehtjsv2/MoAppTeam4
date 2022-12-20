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
import java.text.SimpleDateFormat
import java.util.Random
import kotlin.math.*


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
        var fatlist:MutableList<Int> = ArrayList()
        var carlist:MutableList<Int> = ArrayList()
        var prolsit:MutableList<Int> = ArrayList()
        var selectfood2:MutableList<String> = ArrayList()

        var fproi = 0
        var ffati = 0
        var fcari = 0

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

            var F_name_pos2 = c3.getColumnIndex("fat")
            allfood.add(c3.getString(F_name_pos))
        }
        for(i in 0 ..9) {
            num = random.nextInt(allfood.size)
            var c = SplashActivity.moappDB.rawQuery("SELECT * FROM FOOD where F_ID = '${allfood[num]}'",null)
            c.moveToNext()
            var inde = c.getColumnIndex("fat")
            fatlist.add(c.getInt(inde))
            inde = c.getColumnIndex("carbo")
            carlist.add(c.getInt(inde))
            inde = c.getColumnIndex("protein")
            prolsit.add(c.getInt(inde))
            selectfood.add(allfood[num])
            allfood.removeAt(num)
        }

        //등수정하기 후에
        val currentTime : Long = System.currentTimeMillis()
        Log.d("ppp",currentTime.toString())
        val currentYear= SimpleDateFormat("YYYY").format(currentTime)
        val currentMonth= SimpleDateFormat("MM").format(currentTime)
        val currentDay= SimpleDateFormat("dd").format(currentTime)
        val currentHH= SimpleDateFormat("HH").format(currentTime).toInt()


        val compareDate=currentYear+"-"+currentMonth+"-"+currentDay
        var m = "-"
        var l = "-"
        var d = "-"

        Log.d("TAG11",compareDate)
        val c5 = SplashActivity.moappDB.rawQuery("select * from FOODRECENT where Date_eat like '${compareDate}%';",null)
        while (c5.moveToNext()){
            var recentin = c5.getColumnIndex("Date_eat")
            var recentdate = c5.getString(recentin)

            var ar=recentdate.split(" ")
            var time=ar[1]
            var HH=time.split(":")


            var recentf = c5.getColumnIndex("food_eat_ID")
            var recentname = c5.getString(recentf)

            if(HH[0].toInt() < 12 && HH[0].toInt()>= 6){
                m = recentname
            }
            else if(HH[0].toInt()<18 && HH[0].toInt()>=12){
                l = recentname
            }
            else if(HH[0].toInt()<24 || HH[0].toInt()<6) {
                d = recentname
            }
        }


        val c6 = SplashActivity.moappDB.rawQuery("select * from RECOMMENDNUTRIENT where C_id = '1'",null)
        c6.moveToNext()

        var proi = c6.getColumnIndex("RN_protein")
        var pro = c6.getInt(proi)
        var fati = c6.getColumnIndex("RN_fat")
        var fat = c6.getInt(fati)
        var cari = c6.getColumnIndex("RN_carbo")
        var car = c6.getInt(cari)

        var standardp =0
        var standardf =0
        var standardc =0

        var eatp = 0
        var eatf = 0
        var eatc = 0

        if(currentHH< 12 && currentHH>= 6){
            if(m.equals("-")){
                standardp = (pro/3).toInt()
                standardf =(fat/3).toInt()
                standardc =(car/3).toInt()

                eatp = 0
                eatf = 0
                eatc = 0
            }
            else{
                standardp = (pro/3).toInt()
                standardf =(fat/3).toInt()
                standardc =(car/3).toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}'",null)
                c6.moveToNext()
                fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)

            }
        }
        else if(currentHH<18 && currentHH>=12){
            if(m.equals("-")&&l.equals("-")){
                standardp = (pro*2/3).toInt()
                standardf =(fat*2/3).toInt()
                standardc =(car*2/3).toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

            }
            else if(m.equals("-") && !l.equals("-")){
                standardp = (pro*2/3).toInt()
                standardf =(fat*2/3).toInt()
                standardc =(car*2/3).toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c6.moveToNext()
                fproi = c6.getColumnIndex("protein")
                eatp += c6.getInt(fproi)
                ffati = c6.getColumnIndex("fat")
                eatf += c6.getInt(ffati)
                fcari = c6.getColumnIndex("carbo")
                eatc += c6.getInt(fcari)

            }
            else if(!m.equals("-") && l.equals("-")){
                standardp = (pro*2/3).toInt()
                standardf =(fat*2/3).toInt()
                standardc =(car*2/3).toInt()
                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)


            }
            else if(!m.equals("-") && !l.equals("-")){
                standardp = (pro*2/3).toInt()
                standardf =(fat*2/3).toInt()
                standardc =(car*2/3).toInt()
                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)

                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)

            }
        }
        else if(currentHH<24 || currentHH<6) {
            if(m.equals("-")&&!l.equals("-")&&d.equals("-")){ // xOX
                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)




            }
            else if(m.equals("-")&&!l.equals("-")&&!d.equals("-")){ //xOO
                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${d}' ",null)
                c6.moveToNext()
                fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)


                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)




            }
            else if(m.equals("-")&&l.equals("-")&&d.equals("-")){ //xXX
                    standardp = pro.toInt()
                    standardf = fat.toInt()
                    standardc = car.toInt()

                    eatp = (pro*2/3).toInt()
                    eatf = (fat*2/3).toInt()
                    eatc = (car*2/3).toInt()
            }
            else if(m.equals("-")&&l.equals("-")&&!d.equals("-")){//xXO
                    standardp = pro.toInt()
                    standardf = fat.toInt()
                    standardc = car.toInt()

                    eatp = (pro*2/3).toInt()
                    eatf = (fat*2/3).toInt()
                    eatc = (car*2/3).toInt()

                    val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${d}' ",null)
                    c6.moveToNext()
                    var fproi = c6.getColumnIndex("protein")
                    eatp += c6.getInt(fproi)
                    var ffati = c6.getColumnIndex("fat")
                    eatf += c6.getInt(ffati)
                    var fcari = c6.getColumnIndex("carbo")
                    eatc += c6.getInt(fcari)

            }
            else if(!m.equals("-")&&l.equals("-")&&d.equals("-")){//OXX
                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                var fproi = c6.getColumnIndex("protein")
                eatp += c6.getInt(fproi)
                var ffati = c6.getColumnIndex("fat")
                eatf += c6.getInt(ffati)
                var fcari = c6.getColumnIndex("carbo")
                eatc += c6.getInt(fcari)

            }
            else if(!m.equals("-")&&l.equals("-")&&!d.equals("-")){//OXO
                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                eatp = (pro/3).toInt()
                eatf = (fat/3).toInt()
                eatc = (car/3).toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                var fproi = c6.getColumnIndex("protein")
                eatp += c6.getInt(fproi)
                var ffati = c6.getColumnIndex("fat")
                eatf += c6.getInt(ffati)
                var fcari = c6.getColumnIndex("carbo")
                eatc += c6.getInt(fcari)

                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${d}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)



            }
            else if(!m.equals("-")&&!l.equals("-")&&d.equals("-")){//OOX

                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                var fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                var ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                var fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)

                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)

            }
            else if(!m.equals("-")&&!l.equals("-")&&!d.equals("-")){//OOO

                standardp = pro.toInt()
                standardf = fat.toInt()
                standardc = car.toInt()

                val c6 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${m}' ",null)
                c6.moveToNext()
                var fproi = c6.getColumnIndex("protein")
                eatp = c6.getInt(fproi)
                var ffati = c6.getColumnIndex("fat")
                eatf = c6.getInt(ffati)
                var fcari = c6.getColumnIndex("carbo")
                eatc = c6.getInt(fcari)

                val c7 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${l}' ",null)
                c7.moveToNext()
                fproi = c7.getColumnIndex("protein")
                eatp += c7.getInt(fproi)
                ffati = c7.getColumnIndex("fat")
                eatf += c7.getInt(ffati)
                fcari = c7.getColumnIndex("carbo")
                eatc += c7.getInt(fcari)

                val c8 = SplashActivity.moappDB.rawQuery("select * from food where F_name = '${d}' ",null)
                c8.moveToNext()
                fproi = c8.getColumnIndex("protein")
                eatp += c8.getInt(fproi)
                ffati = c8.getColumnIndex("fat")
                eatf += c8.getInt(ffati)
                fcari = c8.getColumnIndex("carbo")
                eatc += c8.getInt(fcari)

            }
        }


        var needf:Double = eatf/standardf.toDouble()
        var needc:Double = eatc/standardc.toDouble()
        var needp:Double = eatp/standardp.toDouble()

        var n1 = 0
        var n2 = 0
        var n3 = 0

        // 지탄단이 필요
        if(needf >= needc){
            n1 +=1
        }
        else{
            n2 +=1
        }

        if(needf >= needp){
            n1 +=1
        }
        else{
            n3 +=1
        }

        if(needc >= needp){
            n2 +=1
        }
        else{
            n3 +=1
        }

        var needf2 = standardf-eatf
        var needc2 = standardc-eatc
        var needp2 = standardp-eatp

        var temp = 0
        var goodindex = 0
        var goodvalue = 10000
        // 순위별
        if(n1 == 2){
            for(i in 0..9) {
                temp = needf2 - fatlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n2 ==2){
            for(i in 0..9) {
                temp = needc2 - carlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n3 ==2){
            for(i in 0..9) {
                temp = needp2 -prolsit[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }

        temp = 0
        goodindex = 0
        goodvalue = 10000


        if(n1 == 1){
            for(i in 0..8) {
                temp = needf2 - fatlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n2 ==1){
            for(i in 0..8) {
                temp = needc2 - carlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n3 ==1){
            for(i in 0..8) {
                temp = needp2 -prolsit[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }

        if(n1 == 0){
            for(i in 0..7) {
                temp = needf2 - fatlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n2 ==0){
            for(i in 0..7) {
                temp = needc2 - carlist[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }
        else if(n3 ==0){
            for(i in 0..7) {
                temp = needp2 -prolsit[i]
                if(goodvalue>abs(temp)){
                    goodindex = i
                    goodvalue = abs(temp)
                }
            }
            selectfood2.add(selectfood[goodindex])
            selectfood.removeAt(goodindex)
        }



        num = random.nextInt(100)
        if(num<=49){
            Log.d("pjy",selectfood2[0])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood2[0]}'"
            val c4 = SplashActivity.moappDB.rawQuery(sql4,null)
            c4.moveToNext()
            var F_name_pos = c4.getColumnIndex("F_name")
            var fname = c4.getString(F_name_pos)
            Log.d("pjy",fname)
            binding.home2MenuName.text = fname
            food_name = fname
        }
        else if(num <= 79){
            Log.d("pjy",selectfood2[1])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood2[1]}'"
            val c4 = SplashActivity.moappDB.rawQuery(sql4,null)
            c4.moveToNext()
            var F_name_pos = c4.getColumnIndex("F_name")
            var fname = c4.getString(F_name_pos)
            Log.d("pjy",fname)
            binding.home2MenuName.text = fname
            food_name = fname
        }
        else{
            Log.d("pjy",selectfood2[2])
            val sql4 = "SELECT * FROM FOOD where F_ID = '${selectfood2[2]}'"
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