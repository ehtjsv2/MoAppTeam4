package com.example.todayseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.Login.LoginActivity3_3
import com.example.todayseat.Login.LoginActivity5
import com.example.todayseat.databinding.ActivityLogin4Binding
import com.example.todayseat.databinding.SearchListviewBinding
import com.example.todayseat.Login.MenuListAdapter
import com.example.todayseat.SplashActivity.Companion.moappDB

/*
interface OnItemClick {
    fun onClick(value: String)
}
*/
class MyViewHolder(val binding: SearchListviewBinding) :
    RecyclerView.ViewHolder(binding.root){
    fun bind(item:String){
        binding.label.text = item
    }
}
/*
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

*/
class MyAdapter2(val context: Context, val datas: MutableList<String> ) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding:SearchListviewBinding

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



class LoginActivity4 : AppCompatActivity(){

    //처리해야할 데이터
    var like_list:MutableList<String> = ArrayList()
    // 검색리스트
    var all_list:MutableList<String> = ArrayList()
    var search_list:MutableList<String> = ArrayList()


    lateinit var binding: ActivityLogin4Binding
    lateinit var adapter2:MyAdapter2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin4Binding.inflate(layoutInflater)

        setContentView(binding.root)


        settingList()
        var adapter = MenuListAdapter(search_list)
        binding.SearchRecycler.adapter=adapter
        binding.SearchRecycler.layoutManager = LinearLayoutManager(this)

        adapter2 = MyAdapter2(this,like_list)
        binding.EatRecycler.adapter=adapter2
        binding.EatRecycler.layoutManager = LinearLayoutManager(this)


        adapter.setItemClickListener(object : MenuListAdapter.OnItemClickListener{
            override fun onClick(selectedMenu: String) {
                var count:Int = 0
                var index:Int = -1

                for(i in 0..like_list.size-1){
                    if(like_list[i].contains(selectedMenu)){
                        count = 1;
                        index = i;
                    }
                }
                if(count == 1){
                    like_list.removeAt(index)
                    adapter2.notifyDataSetChanged()
                }
                else {
                    like_list.add(selectedMenu)
                    adapter2.notifyDataSetChanged()
                    //Log.d("pjy","어댑터 $item")
                }
            }
        } )


//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)


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

            var score = 0
            for(i in 0..like_list.size-1) {
                var sql1 = "SELECT category_app FROM food where F_NAME='${like_list[i]}'"
                Log.d("test", like_list[i].toString())
                var c = moappDB.rawQuery(sql1, null)
                c.moveToNext()
                var nameindex = c.getColumnIndex("category_app")
                var str1 = c.getString(nameindex)

                if (str1.toString().equals('1')) {
                    var sql2 = "SELECT meat FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("meat")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET meat = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('2')) {
                    var sql2 = "SELECT seafood FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("seafood")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET seafood = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('3')) {
                    var sql2 = "SELECT vegetable FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("vegetable")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET vegetable = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('4')) {
                    var sql2 = "SELECT noodle FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("noodle")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET noodle = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('5')) {
                    var sql2 = "SELECT snack_bar FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("snack_bar")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET snack_bar = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('6')) {
                    var sql2 = "SELECT korean FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("korean")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET korean = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('7')) {
                    var sql2 = "SELECT rice FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("rice")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET rice = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }

                if (str1.toString().equals('8')) {
                    var sql2 = "SELECT etc FROM foodfavor where Favor_ID=1"
                    var c2 = moappDB.rawQuery(sql2, null)
                    c2.moveToNext()
                    var foodindex = c2.getColumnIndex("etc")
                    score = c2.getInt(foodindex) + 1
                    var sql3 = "UPDATE foodfavor SET etc = ${score} where Favor_ID = 1"
                    SplashActivity.moappDB.execSQL(sql3)
                }
            }



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
        val sql = "SELECT * FROM FOOD"
        val c = SplashActivity.moappDB.rawQuery(sql,null)
        while (c.moveToNext()){
            var F_name_pos = c.getColumnIndex("F_name")
            all_list.add(c.getString(F_name_pos))
        }
    }
}