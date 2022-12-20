package com.example.todayseat.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todayseat.R
import com.example.todayseat.databinding.FragmentHome3Binding

private var _binding: FragmentHome3Binding? = null
private val binding get() = _binding!!
class HomeFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentHome3Binding.inflate(inflater, container, false)
        //요리하기버튼
        binding.cook.setOnClickListener {
            loadFragment(RecipeFragment())
        }
        binding.nScoreNext.setOnClickListener {
            val dlg2=CustomMenuScoreDialog(requireActivity())
            dlg2.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg2.setCancelable(false)
            dlg2.show()
        }
        //좋아요 버튼 눌리면
        binding.likeFood.setOnClickListener{

        }
        //싫어요 버튼 눌리면
        binding.dislikeFood.setOnClickListener {

        }
        return binding.root
    }
    private fun loadFragment(fragment: Fragment) {
        Log.d("clickTest", "click!->" + fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //test test
    }
    companion object{
        var food_name="우동(중식)"
    }
}