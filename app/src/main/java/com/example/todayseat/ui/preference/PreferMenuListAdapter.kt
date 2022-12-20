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

        var prefer_score1 : Int = 0
        var prefer_score2 : Int = 0
        var prefer_score3 : Int = 0
        var prefer_score4 : Int = 0
        var prefer_score5 : Int = 0
        var prefer_score6 : Int = 0
        var prefer_score7 : Int = 0
        var prefer_score8 : Int = 0

        val sql = "SELECT * FROM FOODCATEGORY"
        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
        Log.d("DB1234","Preference cursor")
        while(c.moveToNext()){
            var meat_pos = c.getColumnIndex("meat")
            prefer_score1 = c.getInt(meat_pos)

            var sea_pos = c.getColumnIndex("seafood")
            prefer_score2 = c.getInt(sea_pos)

            var veg_pos = c.getColumnIndex("vegetable")
            prefer_score3 = c.getInt(veg_pos)

            var noodle_pos = c.getColumnIndex("noodle")
            prefer_score4 = c.getInt(noodle_pos)

            var snack_pos = c.getColumnIndex("snack_bar")
            prefer_score5 = c.getInt(snack_pos)

            var korean_pos = c.getColumnIndex("korean")
            prefer_score6 = c.getInt(korean_pos)

            var rice_pos = c.getColumnIndex("rice")
            prefer_score7 = c.getInt(rice_pos)

            var etc_pos = c.getColumnIndex("etc")
            prefer_score8 = c.getInt(etc_pos)

        }

        //이 선호도 페이지를 나갔을 때 선호도에 대한 값을 업데이트하게 바꾸기
        //data binding
        Log.d("TAG11",current.menuImg.toString())
        Glide.with(holder.itemView)
            .load("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjEyMDVfMjYw%2FMDAxNjcwMjA5MTYzNTk2.au2fM0nj00Rz6umaLHsnrp3-ig4qvCqi0NnaOhvZC9Yg.WBoK-t_XNCpMEgmqQnktCck4mDXIAV_yjNQX2-uaZ8kg.JPEG.dlgusrud6464%2FIMG_3882.JPG&type=a340")
            .into(binding.preferMenuImage)
        binding.preferMenuName.text = current.menuName
        binding.preferMenuClass.text=current.menuClass

        //싫어요, 좋아요, 최고에요 클릭이벤트, -1, 1, 2
        //클릭한 것에 대한 코드이다
        binding.preferMenuBadImg.setOnClickListener {
            if(binding.preferMenuBadImg.isSelected==false){
                //preferMenuClass의 종류에 따라서 어느 preference_score의 값을 조정할지 결정
                //싫어요 눌러서 활성화하기
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBadImg.isSelected=true
                binding.preferMenuGoodImg.isSelected=false
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuBestImg.isSelected=false
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 -= 1
                    "2" -> prefer_score2 -= 1
                    "3" -> prefer_score3 -= 1
                    "4" -> prefer_score4 -= 1
                    "5" -> prefer_score5 -= 1
                    "6" -> prefer_score6 -= 1
                    "7" -> prefer_score7 -= 1
                    "8" -> prefer_score8 -= 1
                }
            }
            else{
                //싫어요 비활성화하기
                //메뉴이름 받아오기
                binding.preferMenuName.text.toString()
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBadImg.isSelected=false
                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 += 1
                    "2" -> prefer_score2 += 1
                    "3" -> prefer_score3 += 1
                    "4" -> prefer_score4 += 1
                    "5" -> prefer_score5 += 1
                    "6" -> prefer_score6 += 1
                    "7" -> prefer_score7 += 1
                    "8" -> prefer_score8 += 1
                }
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

                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 += 1
                    "2" -> prefer_score2 += 1
                    "3" -> prefer_score3 += 1
                    "4" -> prefer_score4 += 1
                    "5" -> prefer_score5 += 1
                    "6" -> prefer_score6 += 1
                    "7" -> prefer_score7 += 1
                    "8" -> prefer_score8 += 1
                }
            }
            else{
                // 좋아요 였는데 그것을 눌러서 버튼을 없앴을 경우
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuGoodImg.isSelected=false

                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 -= 1
                    "2" -> prefer_score2 -= 1
                    "3" -> prefer_score3 -= 1
                    "4" -> prefer_score4 -= 1
                    "5" -> prefer_score5 -= 1
                    "6" -> prefer_score6 -= 1
                    "7" -> prefer_score7 -= 1
                    "8" -> prefer_score8 -= 1
                }
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
                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 += 2
                    "2" -> prefer_score2 += 2
                    "3" -> prefer_score3 += 2
                    "4" -> prefer_score4 += 2
                    "5" -> prefer_score5 += 2
                    "6" -> prefer_score6 += 2
                    "7" -> prefer_score7 += 2
                    "8" -> prefer_score8 += 2
                }
            }
            // 최고에요를 비활성화하기
            else{
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
                binding.preferMenuBestImg.isSelected=false
                when(binding.preferMenuClass.text){
                    "1" -> prefer_score1 -= 2
                    "2" -> prefer_score2 -= 2
                    "3" -> prefer_score3 -= 2
                    "4" -> prefer_score4 -= 2
                    "5" -> prefer_score5 -= 2
                    "6" -> prefer_score6 -= 2
                    "7" -> prefer_score7 -= 2
                    "8" -> prefer_score8 -= 2
                }
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