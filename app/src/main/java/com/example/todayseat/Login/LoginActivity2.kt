package com.example.todayseat.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todayseat.R
import com.example.todayseat.databinding.ActivityLogin2Binding


class LoginActivity2 : AppCompatActivity() {

    //처리해야할 데이터
    var activity_index: Int = -1 // 1인경우 row 2인경우 middle 3인 경우 high

    lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener {
            //Log.d("pjy", activity_index.toString())
            if(activity_index == -1){
                AlertDialog.Builder(this@LoginActivity2).setTitle("항목을 선택해야 합니다")
                    .setCancelable(true).setNegativeButton("확인", null)
                    .show()
            }
            else {
                val intent = Intent(this, LoginActivity3::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.groupAI.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.AI_row -> {
                    activity_index = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.AI_Middle -> {
                    activity_index = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.AI_High -> {
                    activity_index = 3
                    //Log.d("pjy", activity_index)
                }
            }
        }
    }
}