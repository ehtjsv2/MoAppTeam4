package com.example.todayseat.ui.preference

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.PreferMenuItemBinding


class PreferMenuListAdapter(val menus: MutableList<Menu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable{
    class MyViewHolder(val binding: PreferMenuItemBinding):RecyclerView.ViewHolder(binding.root)

    private var files = menus

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        Log.d("TAG11","onCreateViewHolder")
        return MyViewHolder(PreferMenuItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG11","onBIndViewHolder")
        val binding = (holder as MyViewHolder).binding
        val current= files.get(position)
        //preference_score도 8가지로 종류를 바꾸고 menuclass의 정보를 받아서 일치하는 것의 값을 조절해주는 것으로 하자

        //array형으로 바꾸어서 입력을 받기
//        var prefer_score = IntArray(8)
//        var column_name  = ""
//        val sql = "SELECT * FROM FOODFAVOR"
//        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
//        Log.d("DB1234","Preference cursor")
//        while(c.moveToNext()){
//            var meat_pos = c.getColumnIndex("meat")
//            prefer_score[0] = c.getInt(meat_pos)
//
//            var sea_pos = c.getColumnIndex("seafood")
//            prefer_score[1] = c.getInt(sea_pos)
//
//            var veg_pos = c.getColumnIndex("vegetable")
//            prefer_score[2] = c.getInt(veg_pos)
//
//            var noodle_pos = c.getColumnIndex("noodle")
//            prefer_score[3] = c.getInt(noodle_pos)
//
//            var snack_pos = c.getColumnIndex("snack_bar")
//            prefer_score[4] = c.getInt(snack_pos)
//
//            var korean_pos = c.getColumnIndex("korean")
//            prefer_score[5] = c.getInt(korean_pos)
//
//            var rice_pos = c.getColumnIndex("rice")
//            prefer_score[6] = c.getInt(rice_pos)
//
//            var etc_pos = c.getColumnIndex("etc")
//            prefer_score[7] = c.getInt(etc_pos)
//
//        }

        //이 선호도 페이지를 나갔을 때 선호도에 대한 값을 업데이트하게 바꾸기
        //data binding
        Log.d("TAG11",current.menuImg.toString())
        Glide.with(holder.itemView)
            .load(current.menuImg)
            .into(binding.preferMenuImage)
        binding.preferMenuName.text = current.menuName
        binding.preferMenuClass.text=current.menuClass

        //싫어요, 좋아요, 최고에요 클릭이벤트, -1, 1, 2
        //클릭한 것에 대한 코드이다
        binding.preferMenuBadImg.setOnClickListener {
            if(binding.preferMenuBadImg.isSelected==false){
                //preferMenuClass의 종류에 따라서 어느 preference_score의 값을 조정할지 결정
                //싫어요 눌러서 활성화하기
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad_pink)
                binding.preferMenuBadImg.isSelected=true
                binding.preferMenuGoodImg.isSelected=false
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuBestImg.isSelected=false
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)

//                when(binding.preferMenuClass.text){
//                    "1" -> column_name  = "meat"
//                    "2" -> column_name  = "seafood"
//                    "3" -> column_name  = "vegetable"
//                    "4" -> column_name  = "noodle"
//                    "5" -> column_name  = "snack_bar"
//                    "6" -> column_name  = "korean"
//                    "7" -> column_name  = "rice"
//                    "8" -> column_name  = "etc"
//                }
//                var sql = "SELECT ${column_name} FROM FOODFAVOR"
//                val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
//                val score_pos = c.getColumnIndex("${column_name}")
//                var score = c.getInt(score_pos)
//                var modify_score : Int = score - 1
//                var modify = modify_score.toString()
//                Log.d("DB1234","싫어요 활성화하기")
//
//                sql = "Update FOODFAVOR Set ${column_name} = ${modify} WHERE Favor_ID = '1'"
//                SplashActivity.moappDB.execSQL(sql)

//                Log.d("DB1234",prefer_score.toList().toString())
            }
            else{
                //싫어요 비활성화하기
                //메뉴이름 받아오기
                binding.preferMenuName.text.toString()
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBadImg.isSelected=false
//                when(binding.preferMenuClass.text){
//                    "1" -> prefer_score[0] += 1
//                    "2" -> prefer_score[1] += 1
//                    "3" -> prefer_score[2] += 1
//                    "4" -> prefer_score[3] += 1
//                    "5" -> prefer_score[4] += 1
//                    "6" -> prefer_score[5] += 1
//                    "7" -> prefer_score[6] += 1
//                    "8" -> prefer_score[7] += 1
//                }
//                Log.d("DB1234",prefer_score.toList().toString())
            }
        }
        binding.preferMenuGoodImg.setOnClickListener {
            if(binding.preferMenuGoodImg.isSelected==false){
                //false 안의 if문이 좋아요가 눌렸을 때
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good_pink)
                binding.preferMenuGoodImg.isSelected=true
                binding.preferMenuBadImg.isSelected=false
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBestImg.isSelected=false
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)

//                when(binding.preferMenuClass.text){
//                    "1" -> prefer_score[0] += 1
//                    "2" -> prefer_score[1] += 1
//                    "3" -> prefer_score[2] += 1
//                    "4" -> prefer_score[3] += 1
//                    "5" -> prefer_score[4] += 1
//                    "6" -> prefer_score[5] += 1
//                    "7" -> prefer_score[6] += 1
//                    "8" -> prefer_score[7] += 1
//                }
//                Log.d("DB1234",prefer_score.toList().toString())
            }
            else{
                // 좋아요 였는데 그것을 눌러서 버튼을 없앴을 경우
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuGoodImg.isSelected=false
//
//                when(binding.preferMenuClass.text){
//                    "1" -> prefer_score[0] -= 1
//                    "2" -> prefer_score[1] -= 1
//                    "3" -> prefer_score[2] -= 1
//                    "4" -> prefer_score[3] -= 1
//                    "5" -> prefer_score[4] -= 1
//                    "6" -> prefer_score[5] -= 1
//                    "7" -> prefer_score[6] -= 1
//                    "8" -> prefer_score[7] -= 1
//                }
//                Log.d("DB1234",prefer_score.toList().toString())
            }
        }
        binding.preferMenuBestImg.setOnClickListener {
            //최고에요를 눌렀는데 활성화 된것
            if(binding.preferMenuBestImg.isSelected==false){
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart_pink)
                binding.preferMenuBestImg.isSelected=true
                binding.preferMenuBadImg.isSelected=false
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuGoodImg.isSelected=false
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
//                when(binding.preferMenuClass.text){
//                    "1" -> prefer_score[0] += 2
//                    "2" -> prefer_score[1] += 2
//                    "3" -> prefer_score[2] += 2
//                    "4" -> prefer_score[3] += 2
//                    "5" -> prefer_score[4] += 2
//                    "6" -> prefer_score[5] += 2
//                    "7" -> prefer_score[6] += 2
//                    "8" -> prefer_score[7] += 2
//                }
//                Log.d("DB1234",prefer_score.toList().toString())
            }
            // 최고에요를 비활성화하기
            else{
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
                binding.preferMenuBestImg.isSelected=false
//                when(binding.preferMenuClass.text){
//                    "1" -> prefer_score[0] -= 2
//                    "2" -> prefer_score[1] -= 2
//                    "3" -> prefer_score[2] -= 2
//                    "4" -> prefer_score[3] -= 2
//                    "5" -> prefer_score[4] -= 2
//                    "6" -> prefer_score[5] -= 2
//                    "7" -> prefer_score[6] -= 2
//                    "8" -> prefer_score[7] -= 2
//                }
//                Log.d("DB1234",prefer_score.toList().toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                files = if (charString.isEmpty()) {
                    menus
                } else {
                    var filteredList = mutableListOf<Menu>()
                    if (menus != null) {
                        for (name in menus) {
                            if (name.menuName.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(name);
                            }
                        }

                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = files
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                files = p1?.values as MutableList<Menu>
                notifyDataSetChanged()
            }

        }
    }

}



//class preferMenuListAdapter(val menus: MutableList<Menu>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
//    class MyViewHolder(val binding: PreferMenuItemBinding) : RecyclerView.ViewHolder(binding.root)
//
//
//    private var files = menus
//    private var isEmpty=1
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return MyViewHolder(
//            PreferMenuItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent, false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val binding = (holder as MyViewHolder).binding
//        val current= files?.get(position)
//        var strImg = current?.menuImg
//        binding.preferMenuName.text = current?.menuName
//        Glide.with(holder.itemView) .load("https://media.vlpt.us/images/sasy0113/post/f7085683-1a62-4ce7-9f7f-e8fd2f3ec825/Android%20Kotlin.jpg")
//            .into(binding.preferMenuImage)
//        binding.preferMenuClass.text = current?.menuClass
//
//
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(binding.preferMenuName.text.toString())
////            binding.menuListLayout.isSelected=binding.menuListLayout.isSelected!=true
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return files.size
//    }
//
//    interface OnItemClickListener {
//        fun onClick(selectedMenu:String)
//    }
//
//
//    fun setItemClickListener(onItemClickListener: preferMenuListAdapter.OnItemClickListener) {
//        this.itemClickListener = onItemClickListener
//    }
//
//    private lateinit var itemClickListener: preferMenuListAdapter.OnItemClickListener
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                val charString = constraint.toString()
//                files = if (charString.isEmpty()) {
//                    menus
//                } else {
//                    var filteredList = mutableListOf<Menu>()
//                    if (menus != null) {
//                        for (menu in menus) {
//                            if (menu.menuName.toLowerCase().contains(charString.toLowerCase())) {
//                                filteredList.add(menu);
//
//                            }
//                        }
//
//                    }
//                    filteredList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = files
//                return filterResults
//            }
//
//            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//                files = p1?.values as MutableList<Menu>
//                notifyDataSetChanged()
//            }
//
//        }
//    }
//}