package com.example.todayseat.ui.myPage

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.MyViewHolder
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.FragmentMyPageBinding
import com.example.todayseat.databinding.SearchListviewBinding
import com.example.todayseat.ui.home.CustomMenuDialog
import com.example.todayseat.ui.home.CustomSelect
import com.example.todayseat.ui.home.MenuListAdapter
import java.text.SimpleDateFormat

class dislikeAdapter2(val datas: MutableList<String> ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding: SearchListviewBinding


    override fun getItemCount(): Int {
        return datas.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = SearchListviewBinding.inflate(
            LayoutInflater.from(
                parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = datas[position]
        holder.bind(item)

    }

}

class MyPageFragment : Fragment(){

    private var _binding: FragmentMyPageBinding? = null
    lateinit var adapter2: dislikeAdapter2
    private lateinit var callback: OnBackPressedCallback

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        val root: View = binding.root



        //아침점심저녁 메뉴 바인딩
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
            if(HH[0].toInt()<12 && HH[0].toInt()>=6){ // 아침
                m_index=ID[i].toInt()
            }
            else if(HH[0].toInt()<18 && HH[0].toInt()>=12){ // 점심
                l_index=ID[i].toInt()
            }
            else if(currentHH<24 || currentHH<6){ // 저녁
                e_index=ID[i].toInt()
            }


            Log.d("TAG11","$m_index, $l_index, $e_index")
            if(m_index!=-1){
                val c_m = SplashActivity.moappDB.rawQuery("select food_eat_ID from FOODRECENT where List_ID=$m_index;",null)
                c_m.moveToNext()
                binding.msearch.text=c_m.getString(0)
            }
            if(l_index!=-1){
                val c_l = SplashActivity.moappDB.rawQuery("select food_eat_ID from FOODRECENT where List_ID=$l_index;",null)
                c_l.moveToNext()
                binding.lsearch.text=c_l.getString(0)
            }
            if(e_index!=-1){
                val c_e = SplashActivity.moappDB.rawQuery("select food_eat_ID from FOODRECENT where List_ID=$e_index;",null)
                c_e.moveToNext()
                binding.dsearch.text=c_e.getString(0)
            }

        }


        var menus:MutableList<String> = ArrayList()
        val sql = "SELECT food_eat_ID FROM FOODRECENT"
        val c = SplashActivity.moappDB.rawQuery(sql,null)
        while (c.moveToNext()){
            var F_name_pos = c.getColumnIndex("food_eat_ID")
            menus.add(c.getString(F_name_pos))
        }

        adapter2 = dislikeAdapter2(menus)
        binding.foodl.adapter = adapter2
        binding.foodl.layoutManager = LinearLayoutManager(requireActivity())

        binding.dButten.setOnClickListener {
            val dlg= CustomSelect(requireActivity(),"저녁")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }
        binding.mButton.setOnClickListener {
            val dlg=CustomSelect(requireActivity(),"아침")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }
        binding.lButton.setOnClickListener {
            val dlg=CustomSelect(requireActivity(),"점심")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }

        binding.scoreButten.setOnClickListener {
            val dlg= CustomScore()

            dlg.show(requireActivity().supportFragmentManager,"CustomScore")

        }
        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //여기에 백버튼 눌렀을때 적용될것 작성
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}