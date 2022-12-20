package com.example.todayseat.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.todayseat.MainActivity
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.FragmentRecipeBinding
import com.example.todayseat.databinding.InsertMenuDialogBinding
import java.text.SimpleDateFormat

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
        val cc=SplashActivity.moappDB.rawQuery("select F_url from photo where F_name='${HomeFragment2.food_name}';",null)
        cc.moveToNext()
        val menu_url=cc.getString(0)
        var url_len = menu_url.length - 1
        var ran = IntRange(1,url_len)
        var F_url_sub = menu_url.slice(ran)
        Glide.with(this@RecipeFragment)
            .load(F_url_sub)
            .into(binding.recipeMenuImg)
//        Glide.with(this)
//            .load("https://recipe1.ezmember.co.kr/cache/recipe/2017/02/21/8147779d6a47ae304957c86f1afe58321.jpg")
//            .into(binding.recipeMenuImg)
        val c2=SplashActivity.moappDB.rawQuery("select F_ID,kcal,carbo,protein,fat from food where F_name='${HomeFragment2.food_name}';",null)
        c2.moveToNext()
        var menu_index=c2.getString(0)
        val menu_kcal=c2.getFloat(1)
        val menu_carbo=c2.getFloat(2)
        val menu_protein=c2.getFloat(3)
        val menu_fat=c2.getFloat(4)
        var index=menu_index.toInt()
        if(index>=780){
            index=index-3
            menu_index=index.toString()
        }
        else if(index>=316){
            index=index-2
            menu_index=index.toString()
        }
        else if(index>=278){
            index=index-1
            menu_index=index.toString()
        }
        val c1=SplashActivity.moappDB.rawQuery("select * from receipe where R_ID=$menu_index;",null)
        lateinit var dbIngred:String
        lateinit var dbSeaning:String
        lateinit var dbReceipe:String
//        while(c1.moveToNext()){
//            dbIngred=c1.getString(3)
//            dbSeaning=c1.getString(4)
//            dbReceipe=c1.getString(5)
//            Log.d("TAG11","0:${c1.getString(0)}\n 1:${c1.getString(1)}\n" +
//                    "2:${c1.getString(2)}\n 3:${dbIngred} \n 4:$dbSeaning \n 5:$dbReceipe")
//            //Log.d("TAG11",c1.getString(2))
//        }
        binding.recipeMenuName.text=HomeFragment2.food_name
        binding.recipeKcal.text=menu_kcal.toString()
        binding.recipeCarbo.text=menu_carbo.toString()+"g"
        binding.recipeProt.text=menu_protein.toString()+"g"
        binding.recipeFat.text=menu_fat.toString()+"g"

        c1.moveToNext()
        dbIngred=c1.getString(3)
        dbSeaning=c1.getString(4)
        dbReceipe=c1.getString(5)
        Log.d("TAG11","3:${dbIngred} \n 4:$dbSeaning \n 5:${c1.getString(5)}")
        val arr1 = dbIngred.split("##")
        val arr2 = dbSeaning.split("##")
        val arr3 = dbReceipe.split("##")
        val ingred = mutableListOf<String>()
        val step = mutableListOf<String>()
        for(i in arr1){
            ingred.add(i)
        }
        for(i in arr2){
            ingred.add(i)
        }
        for(i in arr3){
            step.add(i)
        }
        val ingredListAdapter=IngredListAdapter(ingred)
        val setpListAdapter=StepListAdapter(step)
        binding.recipeIngredRecyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=ingredListAdapter
        }
        binding.recipeRecipeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=setpListAdapter
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
        //뒤로가기버튼
        binding.backBtn.setOnClickListener {
            Log.d("TAG11","뒤로가기!")
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                requireActivity().supportFragmentManager.popBackStack()
        }
        //요리했어요버튼
        binding.cookBtn.setOnClickListener {
            Log.d("TAG11","등록전!")
            val currentTime : Long = System.currentTimeMillis()
            val currentYear= SimpleDateFormat("YYYY").format(currentTime)
            val currentMonth= SimpleDateFormat("MM").format(currentTime)
            val currentDay= SimpleDateFormat("dd").format(currentTime)
            val currentHour= SimpleDateFormat("HH").format(currentTime).toInt()
            val currentMin= SimpleDateFormat("mm").format(currentTime).toInt()
            val currentSec= SimpleDateFormat("ss").format(currentTime)
            val date=currentYear+"-"+currentMonth+"-"+currentDay+" "+currentHour+":"+currentMin+":"+currentSec
            val sql ="insert into FOODRECENT(food_eat_ID,Date_eat,C_ID_eat) values ('${binding.recipeMenuName.text}', '$date' ,1);"
            SplashActivity.moappDB.execSQL(sql)
            Log.d("TAG11","등록완료!")
            Toast.makeText(activity, "등록 완료!", Toast.LENGTH_SHORT).show()
            var intent = Intent(requireActivity(),MainActivity::class.java)
            finishAffinity(requireActivity())
            startActivity(intent)
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