package com.example.todayseat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.databinding.IngredientItemBinding

class StepListAdapter(val ingred: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class MyViewHolder(val binding: IngredientItemBinding) : RecyclerView.ViewHolder(binding.root)


    private var files = ingred
    private var isEmpty=1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            IngredientItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val current= files?.get(position)
        binding.ingreName.text = current


        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    interface OnItemClickListener {
        fun onClick(selectedMenu:String)
    }


    fun setItemClickListener(onItemClickListener: IngredListAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: IngredListAdapter.OnItemClickListener

}