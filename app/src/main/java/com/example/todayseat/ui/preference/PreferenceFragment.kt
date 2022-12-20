package com.example.todayseat.ui.preference

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayseat.MainActivity
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.FragmentPreferenceBinding

class PreferenceFragment : Fragment() {

    private var _binding: FragmentPreferenceBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferenceBinding.inflate(inflater, container, false)
        val menus= mutableListOf<Menu>()
        //여기서 메뉴에 대한 이미지와, 이름, 음식군을 가져오면 되겠다
        // menuClass를 1~8까지로 분류

        Log.d("TAG11","before binding adapter")
        val sql = "SELECT f.F_name, f.category_app, p.F_url FROM FOOD f, PHOTO p WHERE f.F_ID = p.P_ID AND f.preference_check = 0  ORDER BY f.F_ID LIMIT 50;"
//        val sql = "SELECT f.F_name, f.category_app, p.F_url FROM FOOD f, PHOTO p WHERE f.F_ID = p.P_ID ORDER BY f.F_ID LIMIT 50;"
//        val sql = "SELECT f.F_name, f.category_app FROM FOOD f  LIMIT 50;"
        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
        Log.d("DB1234","before while_sql")

        //sql문을 통해서 여기있는 음식이름과 카테고리를 넣는다
        while (c.moveToNext()){
            val F_name_pos = c.getColumnIndex("F_name")
            val F_category_pos = c.getColumnIndex("category_app")
            val F_url_pos = c.getColumnIndex("F_url")

            //이미지 url 사용할수 있도록 수정
            var F_url = c.getString(F_url_pos)
            var url_len = F_url.length - 1
            var ran = IntRange(1,url_len)
            var F_url_sub = F_url.slice(ran)

            //menus에 삽입
            menus.add(Menu(F_url_sub,
                c.getString(F_name_pos), c.getString(F_category_pos)))
            Log.d("DB123",F_url_sub)
            Log.d("DB123",c.getString(F_name_pos))
            Log.d("DB123",c.getString(F_category_pos))
        }

        val preferMenuListAdpater =PreferMenuListAdapter(menus)
        binding.preferRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.preferRecyclerView.adapter=preferMenuListAdpater
        binding.preferSearchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("TAG11","text change!!")
                preferMenuListAdpater.filter.filter(p0)
                return true
            }

        })

        val root: View = binding.root

        return root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //여기에 백버튼 눌렀을때 적용될것 작성
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}