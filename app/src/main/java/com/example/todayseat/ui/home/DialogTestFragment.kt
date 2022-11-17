package com.example.todayseat.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayseat.R
import com.example.todayseat.databinding.FragmentDialogTestBinding


class DialogTestFragment : Fragment() {

    private var _binding: FragmentDialogTestBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogTestBinding.inflate(inflater, container, false)
        //여기서부터
        var menus= mutableListOf<String>("치킨","제육볶음","오징어볶음","쏘세지야채볶음","돼지고기김치볶음")
        val menuListAdapter=MenuListAdapter(menus)
        val dlg=CustomMenuDialog(requireActivity())
        dlg.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dlg.setCancelable(false)
        dlg.show()
        //여기까지만 추가
        // Inflate the layout for this fragment
        return binding.root
    }

}