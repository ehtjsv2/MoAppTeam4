package com.example.todayseat.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayseat.*
import com.example.todayseat.databinding.ActivityLogin5Binding



class LoginActivity5 : AppCompatActivity(), OnItemClick {

    //처리해야할 데이터
    var dislike_list:MutableList<String> = ArrayList()
    // 검색리스트
    var all_list:MutableList<String> = ArrayList()
    var search_list:MutableList<String> = ArrayList()


    lateinit var binding: ActivityLogin5Binding
    lateinit var adapter2: MyAdapter2

    override fun onClick(value: String) {
        var count:Int = 0
        var index:Int = -1

        for(i in 0..dislike_list.size-1){
            if(dislike_list[i].contains(value)){
                count = 1;
                index = i;
            }
        }
        if(count == 1){
            dislike_list.removeAt(index)
            adapter2.notifyDataSetChanged()
        }
        else {
            dislike_list.add(value)
            adapter2.notifyDataSetChanged()
            //Log.d("pjy","어댑터 $item")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin5Binding.inflate(layoutInflater)
        setContentView(binding.root)


        settingList()
        var adapter: MyAdapter = MyAdapter(this,search_list,this)
        binding.SearchRecycler.adapter=adapter
        binding.SearchRecycler.layoutManager = LinearLayoutManager(this)

        adapter2 = MyAdapter2(this,dislike_list,this)
        binding.EatRecycler.adapter=adapter2
        binding.EatRecycler.layoutManager = LinearLayoutManager(this)

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
