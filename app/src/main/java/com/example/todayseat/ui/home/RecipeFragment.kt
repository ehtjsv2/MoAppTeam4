package com.example.todayseat.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.todayseat.R
import com.example.todayseat.databinding.FragmentRecipeBinding
import com.example.todayseat.databinding.InsertMenuDialogBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentRecipeBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load("https://recipe1.ezmember.co.kr/cache/recipe/2017/02/21/8147779d6a47ae304957c86f1afe58321.jpg")
            .into(binding.recipeMenuImg)
        val ingred = mutableListOf<String>("돼지고기","김치","대파","양파","고추가루","설탕","간장")
        val ingredListAdapter=IngredListAdapter(ingred)
        binding.recipeIngredRecyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=ingredListAdapter
        }
        binding.recipeDetailBtn01.setOnClickListener{
            Log.d("TAG11","btn01 Click")
            binding.recipeDetailBtn01.isSelected=binding.recipeDetailBtn01.isSelected!=true
            if(binding.recipeDetailBtn01.isSelected==true){
                binding.recipeIngredRecyclerView.layoutParams.height=600
                binding.recipeIngredRecyclerView.foreground=context?.resources?.getDrawable(R.drawable.recipe_ingred_back2)
                binding.recipeDetailBtn01.text="접기▲"
            }
            else{
                binding.recipeIngredRecyclerView.layoutParams.height=300
                binding.recipeIngredRecyclerView.foreground=context?.resources?.getDrawable(R.drawable.recipe_ingred_back)
                binding.recipeDetailBtn01.text="자세히보기▼"
            }

        }
        binding.recipeDetailBtn02.setOnClickListener {
            Log.d("TAG11","btn02 Click")
            binding.recipeDetailBtn02.isSelected=binding.recipeDetailBtn02.isSelected!=true
            if(binding.recipeDetailBtn02.isSelected==true){
                binding.recipeRecipeRecyclerView.layoutParams.height=600
                binding.recipeRecipeRecyclerView.foreground=context?.resources?.getDrawable(R.drawable.recipe_ingred_back2)
                binding.recipeDetailBtn02.text="접기▲"
            }
            else{
                binding.recipeRecipeRecyclerView.layoutParams.height=300
                binding.recipeRecipeRecyclerView.foreground=context?.resources?.getDrawable(R.drawable.recipe_ingred_back)
                binding.recipeDetailBtn02.text="자세히보기▼"
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}