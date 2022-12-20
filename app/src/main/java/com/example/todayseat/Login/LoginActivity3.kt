package com.example.todayseat.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.example.todayseat.databinding.ActivityLogin3Binding

class LoginActivity3 : AppCompatActivity() {

    //처리해야할 데이터
    var meat_score:Int = -1
    var sea_score:Int = -1
    var vege_score:Int = -1

    lateinit var binding:ActivityLogin3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogin3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //Log.d("pjy", meat_score.toString())
        //Log.d("pjy", sea_score.toString())
        //Log.d("pjy", vege_score.toString())


        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener {
            if(meat_score == -1 || sea_score == -1 || vege_score == -1){
                AlertDialog.Builder(this@LoginActivity3).setTitle("항목을 선택해야 합니다")
                    .setCancelable(true).setNegativeButton("확인", null)
                    .show()

            }
            else {
                var sql =
                    "UPDATE foodfavor SET meat = ${meat_score}, seafood = ${sea_score},vegetable = ${vege_score} where Favor_ID = 1"
                SplashActivity.moappDB.execSQL(sql)
                val intent = Intent(this, LoginActivity3_2::class.java)
                startActivity(intent)
                finish()
            }
        }


        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener{
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
            finish()
        }

        binding.meatgroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.meat_score1 -> {
                    meat_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.meat_score2 -> {
                    meat_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.meat_score3 -> {
                    meat_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.meat_score4 -> {
                    meat_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.meat_score5 -> {
                    meat_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
        binding.seagroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.sea_score1 -> {
                    sea_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.sea_score2 -> {
                    sea_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.sea_score3 -> {
                    sea_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.sea_score4 -> {
                    sea_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.sea_score5 -> {
                    sea_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
        binding.vegegroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.vege_score1 -> {
                    vege_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.vege_score2 -> {
                    vege_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.vege_score3 -> {
                    vege_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.vege_score4 -> {
                    vege_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.vege_score5 -> {
                    vege_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }

    }
}