package com.example.todayseat.ui.home

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.InsertMenuDialogBinding
import java.text.SimpleDateFormat


class CustomSelect(var activity: Activity, var menu:String) : Dialog(activity),
    View.OnClickListener {
    val currentTime : Long = System.currentTimeMillis()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        var menus:MutableList<String> = ArrayList()

        val sql = "SELECT * FROM FOOD"
        val c = SplashActivity.moappDB.rawQuery(sql,null)
        while (c.moveToNext()){
            var F_name_pos = c.getColumnIndex("F_name")
            menus.add(c.getString(F_name_pos))
        }
        val menuListAdapter=MenuListAdapter(menus)
        val binding= InsertMenuDialogBinding.inflate(layoutInflater)
        //아침 점심 저녁 text 변경
        if(menu == "아침"){
            binding.searchMenu.queryHint="아침 메뉴 입력"
            binding.insertMenuImg.setImageResource(R.drawable.icon_toast)
        }
        else if(menu == "점심"){
            binding.searchMenu.queryHint="점심 메뉴 입력"
            binding.insertMenuImg.setImageResource(R.drawable.icon_rice)
        }
        else if(menu == "저녁"){
            binding.searchMenu.queryHint="저녁 메뉴 입력"
            binding.insertMenuImg.setImageResource(R.drawable.icon_chicken)
        }

        binding.insertMenuRecyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=menuListAdapter
        }
        binding.searchMenu.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            // 변경 감지되면
            override fun onQueryTextChange(newText: String?): Boolean {
                menuListAdapter.filter.filter(newText)
                //Log.d("TAG11","text changed!!")
                return true
            }
        })
        menuListAdapter.setItemClickListener(object : MenuListAdapter.OnItemClickListener{
            override fun onClick(selectedMenu: String) {
                binding.selectMenuName.text=selectedMenu
            }


        } )
        // 등록하기 버튼클릭시, 메뉴 db에 넣어야됨
        binding.registBtn.setOnClickListener {
            val currentTime : Long = System.currentTimeMillis()
            val currentYear= SimpleDateFormat("YYYY").format(currentTime)
            val currentMonth= SimpleDateFormat("MM").format(currentTime)
            val currentDay= SimpleDateFormat("dd").format(currentTime)
            val currentHH= SimpleDateFormat("HH").format(currentTime).toInt()
            val compareDate=currentYear+"-"+currentMonth+"-"+currentDay
            Log.d("TAG11",compareDate)
            val c1 = SplashActivity.moappDB.rawQuery("select List_ID,Date_eat from FOODRECENT where Date_eat like '${compareDate}%';",null)
            val date= mutableListOf<String>()
            val ID= mutableListOf<String>()
            var count:Int=0
            while(c1.moveToNext()){
                count++
                Log.d("TAG11","id= ${c1.getString(0)}")
                ID.add(c1.getString(0))
                date.add(c1.getString(1))
            }
            Log.d("TAG11",count.toString())
            if(count==0){
                val sql ="insert into FOODRECENT(food_eat_ID,Date_eat,C_ID_eat) values ('${binding.selectMenuName.text}', '$date' ,1);"
                SplashActivity.moappDB.execSQL(sql)
                val c=SplashActivity.moappDB.rawQuery("select * from foodrecent",null)
                while(c.moveToNext()){
                    val F_name_pos=c.getColumnIndex("food_eat_ID")
                    val F_date_pos=c.getColumnIndex("Date_eat")
                    Log.d("TAG11","${c.getString(F_name_pos)} ${c.getString(F_date_pos)}")
                }
                Toast.makeText(activity, "등록 완료!", Toast.LENGTH_SHORT).show()
            }
            else{
                var ar= listOf<String>()
                var time:String
                var HH=listOf<String>()
                var m_index:Int=-1
                var l_index:Int=-1
                var e_index:Int=-1
                for(i in 0..date.size-1){
                    ar=date[i].split(" ") // [i]의 시간이 담김
                    time=ar[1]
                    HH=time.split(":")
                    if(HH[0].toInt()<12){ // 아침
                        m_index=ID[i].toInt()
                    }
                    else if(HH[0].toInt()<18 && HH[0].toInt()>=12){ // 점심
                        l_index=ID[i].toInt()
                    }
                    else if(currentHH<24){ // 저녁
                        e_index=ID[i].toInt()
                    }
                }
                Log.d("TAG11","${m_index} + ${l_index} + $e_index ")
                if(menu=="아침"){
                    Log.d("TAG11","$compareDate 8:00:00")
                    if(m_index!=-1){
                        SplashActivity.moappDB.execSQL("update foodrecent set food_eat_ID='${binding.selectMenuName.text}', Date_eat='$compareDate 8:00:00' where List_ID=$m_index")
                    }
                    else{
                        SplashActivity.moappDB.execSQL("insert into FOODRECENT(food_eat_ID,Date_eat,C_ID_eat) values ('${binding.selectMenuName.text}', '$compareDate 8:00:00' ,1);")
                    }
                }
                else if(menu=="점심"){
                    if(l_index!=-1){
                        SplashActivity.moappDB.execSQL("update foodrecent set food_eat_ID='${binding.selectMenuName.text}', Date_eat='$compareDate 13:00:00' where List_ID=$l_index")
                    }
                    else{
                        SplashActivity.moappDB.execSQL("insert into FOODRECENT(food_eat_ID,Date_eat,C_ID_eat) values ('${binding.selectMenuName.text}', '$compareDate 13:00:00' ,1);")
                    }

                }
                else if(menu=="저녁"){
                    if(e_index!=-1){
                        SplashActivity.moappDB.execSQL("update foodrecent set food_eat_ID='${binding.selectMenuName.text}', Date_eat='$compareDate 20:00:00' where List_ID=$e_index")
                    }
                    else{
                        SplashActivity.moappDB.execSQL("insert into FOODRECENT(food_eat_ID,Date_eat,C_ID_eat) values ('${binding.selectMenuName.text}', '$compareDate 20:00:00' ,1);")
                    }

                }


                //val dlg=CustomMenuDialog(requireActivity())
                //dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                //dlg.setCancelable(false)
            }

            itemClickListener.onClick(binding.selectMenuName.text.toString())
            dismiss()
        }
        // 무시하기 버튼클릭시, 무시
        binding.ignoreBtn.setOnClickListener {
            dismiss()
        }
        setContentView(binding.root)

//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)

    }
    interface CustomDialogListener {
        fun onPositiveClicked(name: String)
        fun onNegativeClicked()
    }

    interface OnItemClickListener {
        fun onClick(selectedMenu:String)
    }


    fun setItemClickListener(onItemClickListener: CustomSelect.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: CustomSelect.OnItemClickListener
    override fun onClick(p0: View?) {
        //TODO("Not yet implemented")
    }

}
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.yes -> {
//            }
//            R.id.no -> dismiss()
//            else -> {
//            }
//        }//Do Something
//        dismiss()
//    }