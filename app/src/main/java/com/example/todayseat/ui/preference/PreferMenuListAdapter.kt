package com.example.todayseat.ui.preference

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todayseat.R
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

        //data binding
        Glide.with(holder.itemView)
            .load(current.menuImg)
            .into(binding.preferMenuImage)
        binding.preferMenuName.text = current.menuName
        binding.preferMenuClass.text=current.menuClass

        //싫어요, 좋아요, 최고에요 클릭이벤트
        binding.preferMenuBadImg.setOnClickListener {
            if(binding.preferMenuBadImg.isSelected==false){
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad_pink)
                binding.preferMenuBadImg.isSelected=true
                binding.preferMenuGoodImg.isSelected=false
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuBestImg.isSelected=false
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
            }
            else{
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBadImg.isSelected=false
            }
        }
        binding.preferMenuGoodImg.setOnClickListener {
            if(binding.preferMenuGoodImg.isSelected==false){
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good_pink)
                binding.preferMenuGoodImg.isSelected=true
                binding.preferMenuBadImg.isSelected=false
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuBestImg.isSelected=false
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
            }
            else{
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
                binding.preferMenuGoodImg.isSelected=false
            }
        }
        binding.preferMenuBestImg.setOnClickListener {
            if(binding.preferMenuBestImg.isSelected==false){
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart_pink)
                binding.preferMenuBestImg.isSelected=true
                binding.preferMenuBadImg.isSelected=false
                binding.preferMenuBadImg.setImageResource(R.drawable.icon_bad)
                binding.preferMenuGoodImg.isSelected=false
                binding.preferMenuGoodImg.setImageResource(R.drawable.icon_good)
            }
            else{
                binding.preferMenuBestImg.setImageResource(R.drawable.icon_heart)
                binding.preferMenuBestImg.isSelected=false
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