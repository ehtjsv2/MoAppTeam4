package com.example.todayseat.ui.myPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todayseat.R
import com.example.todayseat.databinding.FragmentMyPageBinding
import com.example.todayseat.ui.home.CustomMenuDialog
import com.example.todayseat.ui.home.CustomSelect

class MyPageFragment : Fragment(){

    private var _binding: FragmentMyPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dButten.setOnClickListener {
            val dlg= CustomSelect(requireActivity(),"아침")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }
        binding.mButton.setOnClickListener {
            val dlg=CustomSelect(requireActivity(),"점심")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }
        binding.lButton.setOnClickListener {
            val dlg=CustomSelect(requireActivity(),"저녁")
            dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dlg.setCancelable(false)
            dlg.show()
        }

        binding.scoreButten.setOnClickListener {
            val dlg= CustomScore()
            dlg.show(requireActivity().supportFragmentManager,"CustomScore")
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}