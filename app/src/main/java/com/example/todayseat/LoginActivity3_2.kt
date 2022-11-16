package com.example.todayseat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todayseat.databinding.ActivityLogin32Binding


class LoginActivity3_2 : AppCompatActivity() {

    //처리해야할 데이터
    var noodle_score:Int = -1
    var snack_score:Int = -1
    var rice_score:Int = -1

    lateinit var binding: ActivityLogin32Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogin32Binding.inflate(layoutInflater)

        setContentView(binding.root)
        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener{
            if(noodle_score == -1 || snack_score == -1 || rice_score == -1){
                AlertDialog.Builder(this@LoginActivity3_2).setTitle("항목을 선택해야 합니다")
                    .setCancelable(true).setNegativeButton("확인", null)
                    .show()
            }
            else {
                val intent = Intent(this, LoginActivity3_3::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener{
            val intent = Intent(this, LoginActivity3::class.java)
            startActivity(intent)
            finish()
        }


        binding.noodlegroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.noodle_score1 -> {
                    noodle_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.noodle_score2 -> {
                    noodle_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.noodle_score3 -> {
                    noodle_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.noodle_score4 -> {
                    noodle_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.noodle_score5 -> {
                    noodle_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
        binding.snackgroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.snack_score1 -> {
                    snack_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.snack_score2 -> {
                    snack_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.snack_score3 -> {
                    snack_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.snack_score4 -> {
                    snack_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.snack_score5 -> {
                    snack_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
        binding.ricegroup.setOnCheckedChangeListener { Group, CheckedId ->
            when (CheckedId) {
                R.id.rice_score1 -> {
                    rice_score = 1
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", activity_index)
                }
                R.id.rice_score2 -> {
                    rice_score = 2
                    //Log.d("pjy", activity_index)
                }
                R.id.rice_score3 -> {
                    rice_score = 3
                    //Log.d("pjy", activity_index)
                }
                R.id.rice_score4 -> {
                    rice_score = 4
                    //Log.d("pjy", activity_index)
                }
                R.id.rice_score5 -> {
                    rice_score = 5
                    //Log.d("pjy", activity_index)
                }
            }
        }
    }
}