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
import kotlin.math.*


private var _binding: FragmentHome3Binding? = null
private val binding get() = _binding!!
class HomeFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentTime : Long = System.currentTimeMillis()
        _binding = FragmentHome3Binding.inflate(inflater, container, false)
        //요리하기버튼
        val currentTime2 : Long = System.currentTimeMillis()
        val currentYear2= SimpleDateFormat("YYYY").format(currentTime)
        val currentMonth2= SimpleDateFormat("MM").format(currentTime)
        val currentDay2= SimpleDateFormat("dd").format(currentTime)
        val currentHH2= SimpleDateFormat("HH").format(currentTime).toInt()
        val compareDate2=currentYear2+"-"+currentMonth2+"-"+currentDay2
        val c10 = SplashActivity.moappDB.rawQuery("select Date_eat,food_eat_ID from FOODRECENT where Date_eat like '${compareDate2}%';",null)
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
            else if(currentHH2<24 || currentHH2<6){ // 저녁
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

            if(currentHH2.toInt()<12 && currentHH2>=6){
                if(m_H!=-1){

                }else{
                    dlg.show()
                }
            }
            else if(currentHH2<18){
                if(l_H!=-1){

                }
                else{
                    dlg.show()
                }
            }
            else if(currentHH2<24 || currentHH2<6){
                if(d_H!=-1){
                    Log.d("TAG11","current= $currentHH2, HH= ${HH[0]}")
                }
                else{
                    Log.d("TAG11","current= $currentHH2, d_H= $d_H")
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
        val c12 : Cursor
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
            c12=SplashActivity.moappDB.rawQuery("select kcal,carbo,fat,protein from FOOD where F_name= '$l_menu';",null)
            c12.moveToNext()
            l_kcal=c12.getFloat(0)
            l_carbo=c12.getFloat(1)
            l_fat=c12.getFloat(2)
            l_pro=c12.getFloat(3)
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
            binding.nScoreNext.text = myname(fname).toString()
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
            binding.nScoreNext.text = myname(fname).toString()
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
            binding.nScoreNext.text = myname(fname).toString()
        }


        binding.reload.setOnClickListener {
            loadFragment(HomeFragment2())
        }
        binding.cook.setOnClickListener {
            loadFragment(RecipeFragment())
        }
        binding.nScoreNext.setOnClickListener {
            val dlg2=CustomMenuScoreDialog(requireActivity(), food_name)
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

    fun myname(value:String):Int{
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
        val c3 : Cursor
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


        if(currentHH.toInt()<12){ // 아침
            val sql4 = "SELECT * FROM food where F_name = '${value}'"
            val c11 = SplashActivity.moappDB.rawQuery(sql4,null)
            c11.moveToNext()
            var mki= c11.getColumnIndex("kcal")
            m_kcal=c11.getFloat(mki)
            var mpi= c11.getColumnIndex("protein")
            m_pro=c11.getFloat(mpi)
            var mfi= c11.getColumnIndex("carbo")
            m_carbo=c11.getFloat(mfi)
            var mci= c11.getColumnIndex("fat")
            m_fat=c11.getFloat(mci)

        }
        else if(currentHH.toInt()<18 && currentHH.toInt()>=12){ // 점심
            val sql4 = "SELECT * FROM food where F_name = '${value}'"
            val c11 = SplashActivity.moappDB.rawQuery(sql4,null)
            c11.moveToNext()
            var mki= c11.getColumnIndex("kcal")
            l_kcal=c11.getFloat(mki)
            var mpi= c11.getColumnIndex("protein")
            l_pro=c11.getFloat(mpi)
            var mfi= c11.getColumnIndex("carbo")
            l_carbo=c11.getFloat(mfi)
            var mci= c11.getColumnIndex("fat")
            l_fat=c11.getFloat(mci)
        }
        else if(currentHH.toInt()<24){ // 저녁
            val sql4 = "SELECT * FROM food where F_name = '${value}'"
            val c11 = SplashActivity.moappDB.rawQuery(sql4,null)
            c11.moveToNext()
            var mki= c11.getColumnIndex("kcal")
            d_kcal=c11.getFloat(mki)
            var mpi= c11.getColumnIndex("protein")
            d_pro=c11.getFloat(mpi)
            var mfi= c11.getColumnIndex("carbo")
            d_carbo=c11.getFloat(mfi)
            var mci= c11.getColumnIndex("fat")
            d_fat=c11.getFloat(mci)

        }

        val total_kcal=m_kcal+l_kcal+d_kcal
        val total_carbo=m_carbo+l_carbo+d_carbo
        val total_fat=m_fat+l_fat+d_fat
        val total_pro=m_pro+l_pro+d_pro
        Log.d("TAG11","총(탄,지,단)= ${total_kcal}. ${total_carbo}, ${total_fat}, ${total_pro}")

        val sql="select * from food where F_name=' ${HomeFragment2.food_name};"

//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)


        val sql3 = "SELECT * FROM recommendnutrient where C_id=1"
        val c10 = SplashActivity.moappDB.rawQuery(sql3,null)
        c10.moveToNext()
        var kindex= c10.getColumnIndex("RN_kcal")
        var kcal=c10.getFloat(kindex)
        var pindex= c10.getColumnIndex("RN_protein")
        var protein=c10.getFloat(pindex)

        val sql6 = "SELECT age, height, activation, gender FROM CUSTOMER where C_ID=1"
        val c5 = SplashActivity.moappDB.rawQuery(sql6,null)
        c5.moveToNext()
        var check_value:Int = c5.getColumnIndex("height")
        var str1=c5.getInt(check_value)


        check_value = c5.getColumnIndex("activation")
        var str2=c5.getInt(check_value)
        var v = 0
        if( str2 == 1){
            v = 20
        }
        else if(str2 == 2){
            v =30
        }
        else {
            v = 40
        }
        //그래프를 위한 변수 추가
        //영양분 점수 계산식
        // #####################여기를 바꾸시면 적용됩니다
        var nST = 0f //영양분 점수
        var totalKcal = total_kcal // 총섭취칼로리
        // #####################여기를 바꾸시면 적용됩니다

        //고객 정보
        var height = str1
        var activation = v.toFloat()

        //DB 에서 합한 합
        var totalCarbo = total_carbo // 총섭취 탄수화물 (g) 데이터 베이스상 단위 때문에 추가
        var totalFat = total_fat// 총섭취 지방 (g) 데이터 베이스상 단위 때문에 추가
        var totalProtein = total_pro // 총섭취 단백질 (g) 데이터 베이스상 단위 때문에 추가

        var need_protein = protein  // 권장 단백질
        var need_calorie = kcal // 권장 칼로리 : ( 자신의 키 -100 * 0.9 * 활동지수 )


        //건강 지수 계산
        need_calorie =
            ((height - 100f) *0.9f * activation) // 권장 칼로리 (자신의 키 - 100) *0.9 * 활동지수
        totalCarbo=need_calorie*0.6f
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
        if(totalCarbo==0f) carboS=0f
        if(carboS<=0)carboS=0f

        var fat_minus = (totalFat-50f)
        fatS = (100f*0.38f - (fat_minus *9f /(4f+9f) * 0.38f))
        if(fat_minus<=0)fatS=0f
        if(fatS<=0)fatS=0f

        if(proteinS>=25)proteinS=25f
        Log.d("TAG11","칼,탄,지,단 = ${kcalS}, ${carboS}, ${fatS}, ${proteinS}")

        nST = kcalS+carboS+fatS+proteinS
        return nST.toInt()
    }
}