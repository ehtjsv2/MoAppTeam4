package com.example.todayseat.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.*
import com.example.todayseat.databinding.ActivityLogin5Binding
import com.example.todayseat.databinding.SearchListviewBinding
import com.example.todayseat.Login.MenuListAdapter2
import com.example.todayseat.SplashActivity.Companion.moappDB

/*
interface OnItemClick2 {
    fun onClick2(value: String)
}


class dislikeAdapter(val context: Context, val datas: MutableList<String>, listener: OnItemClick2 ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding: SearchListviewBinding
    private val mCallback: OnItemClick2 = listener
    var dislike_list: MutableList<String> = ArrayList()

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = SearchListviewBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = datas[position]
        holder.bind(item)

        binding.root.setOnClickListener {
            var count: Int = 0
            var index: Int = -1
            for (i in 0..dislike_list.size - 1) {
                if (dislike_list[i].contains(item)) {
                    count = 1;
                    index = i;
                }
            }
            if (count == 1) {
                dislike_list.removeAt(index)
                Toast.makeText(this.context, "${item} 취소", Toast.LENGTH_SHORT).show()
                mCallback.onClick2(item)
            } else {
                dislike_list.add(item)
                //Log.d("pjy","어댑터 $item")
                Toast.makeText(this.context, "${item} 선택", Toast.LENGTH_SHORT).show()
                mCallback.onClick2(item)
            }
        }
    }
}

*/

class dislikeAdapter2(val context: Context, val datas: MutableList<String> ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding: SearchListviewBinding


    override fun getItemCount(): Int {
        return datas.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
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

class LoginActivity5 : AppCompatActivity() {

    //처리해야할 데이터
    var dislike_list:MutableList<String> = ArrayList()
    // 검색리스트
    var all_list:MutableList<String> = ArrayList()
    var search_list:MutableList<String> = ArrayList()


    lateinit var binding: ActivityLogin5Binding
    lateinit var adapter2: dislikeAdapter2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin5Binding.inflate(layoutInflater)
        setContentView(binding.root)


        settingList()
        var adapter= MenuListAdapter2(search_list)
        binding.SearchRecycler.adapter=adapter
        binding.SearchRecycler.layoutManager = LinearLayoutManager(this)

        adapter2 = dislikeAdapter2(this,dislike_list)
        binding.EatRecycler.adapter=adapter2
        binding.EatRecycler.layoutManager = LinearLayoutManager(this)

        adapter.setItemClickListener(object : MenuListAdapter.OnItemClickListener{
            override fun onClick(selectedMenu: String) {
                var count:Int = 0
                var index:Int = -1

                for(i in 0..dislike_list.size-1){
                    if(dislike_list[i].contains(selectedMenu)){
                        count = 1;
                        index = i;
                    }
                }
                if(count == 1){
                    dislike_list.removeAt(index)
                    adapter2.notifyDataSetChanged()
                }
                else {
                    dislike_list.add(selectedMenu)
                    adapter2.notifyDataSetChanged()
                    //Log.d("pjy","어댑터 $item")
                }
            }
        } )
        // 데이터 변경 이벤트
        binding.TextSearch.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var Text:String = binding.TextSearch.text.toString()
                //Log.d("pjy",Text)
                search(Text)
                adapter.notifyDataSetChanged()
            }
        })



        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.commitButton.setOnClickListener{
            for(i in 0..dislike_list.size-1) {
                var sql =
                    "INSERT INTO excludefood (C_ID, F_ID\n" +
                            " ) VALUES (1,' ${dislike_list[i]}')"
                SplashActivity.moappDB.execSQL(sql)

                var sql2 =
                    "UPDATE customer SET check_info = 1 where C_ID = 1"
                SplashActivity.moappDB.execSQL(sql2)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener {
            val intent = Intent(this, LoginActivity4::class.java)
            startActivity(intent)
            finish()

        }
    }
    fun search(Text:String){
        search_list.clear()
        if(!Text.isEmpty()){
            for(i in 0..all_list.size-1){
                //Log.d("pjy",all_list.size.toString())
                if(all_list[i].contains(Text)){
                    search_list.add(all_list[i])
                    //Log.d("pjy",all_list[i])
                }
            }
        }
    }

    //추후 데이터 베이스 연동
    fun settingList(){
        val sql = "SELECT * FROM FOOD"
        val c = SplashActivity.moappDB.rawQuery(sql,null)
        while (c.moveToNext()){
            var F_name_pos = c.getColumnIndex("F_name")
            all_list.add(c.getString(F_name_pos))
        }
    }
}

