package com.example.todayseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.Login.LoginActivity3_3
import com.example.todayseat.Login.LoginActivity5
import com.example.todayseat.databinding.ActivityLogin4Binding
import com.example.todayseat.databinding.SearchListviewBinding

interface OnItemClick {
    fun onClick(value: String)
}
interface OnItemClick2 {
    fun onClick2(value: String)
}

class MyViewHolder(val binding: SearchListviewBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            binding.label.text = item
        }
    }

class MyAdapter(val context: Context, val datas: MutableList<String>, listener: OnItemClick ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding: SearchListviewBinding
    private val mCallback: OnItemClick = listener
    var like_list: MutableList<String> = ArrayList()

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
            for (i in 0..like_list.size - 1) {
                if (like_list[i].contains(item)) {
                    count = 1;
                    index = i;
                }
            }
            if (count == 1) {
                like_list.removeAt(index)
                Toast.makeText(this.context, "${item} 취소", Toast.LENGTH_SHORT).show()
                mCallback.onClick(item)
            } else {
                like_list.add(item)
                //Log.d("pjy","어댑터 $item")
                Toast.makeText(this.context, "${item} 선택", Toast.LENGTH_SHORT).show()
                mCallback.onClick(item)
            }
        }
    }
}


class MyAdapter2(val context: Context, val datas: MutableList<String>, listener: OnItemClick ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding:SearchListviewBinding
    private val mCallback: OnItemClick = listener

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



class LoginActivity4 : AppCompatActivity(), OnItemClick {

    //처리해야할 데이터
    var like_list:MutableList<String> = ArrayList()
    // 검색리스트
    var all_list:MutableList<String> = ArrayList()
    var search_list:MutableList<String> = ArrayList()


    lateinit var binding: ActivityLogin4Binding
    lateinit var adapter2:MyAdapter2

    override fun onClick(value: String) {
        var count:Int = 0
        var index:Int = -1

        for(i in 0..like_list.size-1){
            if(like_list[i].contains(value)){
                count = 1;
                index = i;
            }
        }
        if(count == 1){
            like_list.removeAt(index)
            adapter2.notifyDataSetChanged()
        }
        else {
            like_list.add(value)
            adapter2.notifyDataSetChanged()
            //Log.d("pjy","어댑터 $item")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin4Binding.inflate(layoutInflater)

        setContentView(binding.root)


        settingList()
        var adapter:MyAdapter = MyAdapter(this,search_list,this)
        binding.SearchRecycler.adapter=adapter
        binding.SearchRecycler.layoutManager = LinearLayoutManager(this)

        adapter2 = MyAdapter2(this,like_list,this)
        binding.EatRecycler.adapter=adapter2
        binding.EatRecycler.layoutManager = LinearLayoutManager(this)

        // 데이터 변경 이벤트
        binding.TextSearch.addTextChangedListener(object  : TextWatcher{
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
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, LoginActivity5::class.java)
            startActivity(intent)
            finish()
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener {
            val intent = Intent(this, LoginActivity3_3::class.java)
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
        all_list.add("김치찌개")
        all_list.add("떡볶이")
        all_list.add("라면")
        all_list.add("국수")
        all_list.add("국밥")
        all_list.add("김치찜")
        all_list.add("씨리얼")
        all_list.add("북어국")
        all_list.add("탕수육")
        all_list.add("파스타")
    }
}