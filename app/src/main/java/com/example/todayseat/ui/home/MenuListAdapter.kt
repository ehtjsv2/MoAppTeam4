package com.example.todayseat.ui.home

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.R
import com.example.todayseat.databinding.InsertMenuItemBinding

class MenuListAdapter(val menus: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    class MyViewHolder(val binding: InsertMenuItemBinding) : RecyclerView.ViewHolder(binding.root)


    private var files = menus
    private var isEmpty=1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            InsertMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val current= files?.get(position)
        binding.menuName.text = current


        holder.itemView.setOnClickListener {
            itemClickListener.onClick(binding.menuName.text.toString())
//            binding.menuListLayout.isSelected=binding.menuListLayout.isSelected!=true
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    interface OnItemClickListener {
        fun onClick(selectedMenu:String)
    }


    fun setItemClickListener(onItemClickListener: MenuListAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: MenuListAdapter.OnItemClickListener
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                files = if (charString.isEmpty()) {
                    menus
                } else {
                    var filteredList = mutableListOf<String>()
                    if (menus != null) {
                        for (name in menus) {
                            if (name.toLowerCase().contains(charString.toLowerCase())) {
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
                files = p1?.values as MutableList<String>
                notifyDataSetChanged()
            }

        }
    }
}