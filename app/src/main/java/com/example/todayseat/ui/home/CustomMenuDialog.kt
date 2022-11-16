package com.example.todayseat.ui.home

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todayseat.R
import com.example.todayseat.databinding.InsertMenuDialogBinding
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

//class CustomMenuDialog(context: Context,val activity: Activity) {
//    private val dialog= Dialog(context)
//    private lateinit var onClickListener: OnDialogClickListener
//    val binding=InsertMenuDialogBinding.inflate(LayoutInflater.from(context))
//    fun setOnClickListener(listener: OnDialogClickListener){
//        onClickListener=listener
//    }
//
//    val context1=context
//
//    fun showDialog()
//    {
//        var menus= mutableListOf<String>("치킨","제육볶음","오징어볶음","쏘세지야채볶음","돼지고기김치볶음")
//        val menuListAdapter=MenuListAdapter(menus)
//        binding.insertMenuRecyclerView.layoutManager=LinearLayoutManager()
//        binding.insertMenuRecyclerView.adapter=menuListAdapter
//        //
//        dialog.setContentView(R.layout.insert_menu_dialog)
//        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
//        dialog.setCanceledOnTouchOutside(true)
//        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.setCancelable(true)
//        dialog.setTitle("메뉴입력")
//        dialog.show()
//    }
//
//    interface OnDialogClickListener{
//        fun onClicked(name:String)
//    }
//}
class CustomMenuDialog(var activity: Activity) : Dialog(activity),
    View.OnClickListener {
    val currentTime : Long = System.currentTimeMillis()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val currentHour=SimpleDateFormat("HH").format(currentTime).toInt()
        val currentMin=SimpleDateFormat("mm").format(currentTime).toInt()
        var menus= mutableListOf<String>("치킨","제육볶음","오징어볶음","쏘세지야채볶음","돼지고기김치볶음")
        val menuListAdapter=MenuListAdapter(menus)
        val binding= InsertMenuDialogBinding.inflate(layoutInflater)
        //아침 점심 저녁 text 변경
        if(currentHour<12){
            binding.insertMenuDialogTitle.text="오늘 아침은 무엇을 먹었나요?"
        }
        else if(currentHour<18){
            binding.insertMenuDialogTitle.text="오늘 점심은 무엇을 먹었나요?"
        }
        else if(currentHour<=23 && currentMin<=59){
            binding.insertMenuDialogTitle.text="오늘 저녁은 무엇을 먹었나요?"
        }

        binding.insertMenuRecyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=menuListAdapter
        }
        binding.searchMenu.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return true
            }
            // 변경 감지되면
            override fun onQueryTextChange(newText: String?): Boolean {
                menuListAdapter.filter.filter(newText)
                Log.d("TAG11","text changed!!")
                return true
            }
        })
        menuListAdapter.setItemClickListener(object : MenuListAdapter.OnItemClickListener{
            override fun onClick(selectedMenu: String) {
                binding.selectMenuName.text=selectedMenu
            }


        } )
        // 등록하기 버튼클릭시, 메뉴 db에 넣어야됨
        binding.registBtn.setOnClickListener {
            dismiss()
        }
        // 무시하기 버튼클릭시, 무시
        binding.ignoreBtn.setOnClickListener {
            dismiss()
        }
        setContentView(binding.root)

//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        //TODO("Not yet implemented")
    }

}
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.yes -> {
//            }
//            R.id.no -> dismiss()
//            else -> {
//            }
//        }//Do Something
//        dismiss()
//    }