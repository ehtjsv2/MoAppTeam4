package com.example.todayseat.ui.preference

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayseat.MainActivity
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.FragmentPreferenceBinding

class PreferenceFragment : Fragment() {

    private var _binding: FragmentPreferenceBinding? = null
    private val binding get() = _binding!!


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
        val sql = "SELECT f.F_name, f.category_app, p.F_url FROM FOOD f, PHOTO p WHERE f.F_ID = p.P_ID ORDER BY f.F_ID LIMIT 50"
//        val sql = "SELECT f.F_name, f.category_app FROM FOOD f  LIMIT 50"
        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
        Log.d("DB1234","before while_sql")

        //sql문을 통해서 여기있는 음식이름과 카테고리를 넣는다
        while (c.moveToNext()){
            val F_name_pos = c.getColumnIndex("F_name")
            val F_category_pos = c.getColumnIndex("category_app")
            val F_url_pos = c.getColumnIndex("F_url")
//            var F_url = c.getString(F_url_pos)
//            F_url =
//            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20140324_60%2Fbori1758_1395633002962lR3pN_JPEG%2F20140323_125913.jpg&type=ofullfill340_600_png"
            menus.add(Menu(c.getString(F_url_pos),
                c.getString(F_name_pos), c.getString(F_category_pos)))
            Log.d("DB1234",menus[0].toString())
            Log.d("DB1234",c.getString(F_url_pos))
            Log.d("DB1234",c.getString(F_name_pos))
            Log.d("DB1234",c.getString(F_category_pos))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}