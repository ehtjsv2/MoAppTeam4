package com.example.todayseat.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todayseat.LoginActivity4
import com.example.todayseat.R

import com.example.todayseat.databinding.ActivityLogin33Binding

class LoginActivity3_3 : AppCompatActivity() {
    //처리해야할 데이터
    var korean_score:Int = -1
    var etc_score:Int = -1

    lateinit var binding: ActivityLogin33Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin33Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener{
            if(korean_score == -1 || etc_score == -1 ){
                AlertDialog.Builder(this@LoginActivity3_3).setTitle("항목을 선택해야 합니다")
                    .setCancelable(true).setNegativeButton("확인", null)
                    .show()
            }
            else {
                val intent = Intent(this, LoginActivity4::class.java)
                startActivity(intent)
                finish()

            }
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener{
            val intent = Intent(this, LoginActivity3_2::class.java)
            startActivity(intent)
            finish()
        }
        binding.koreangroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.korean_score1 -> {
                    korean_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.korean_score2 -> {
                    korean_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.korean_score3 -> {
                    korean_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.korean_score4 -> {
                    korean_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.korean_score5 -> {
                    korean_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
        binding.etcgroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.etc_score1 -> {
                    etc_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.etc_score2 -> {
                    etc_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.etc_score3 -> {
                    etc_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.etc_score4 -> {
                    etc_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.etc_score5 -> {
                    etc_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
    }
}