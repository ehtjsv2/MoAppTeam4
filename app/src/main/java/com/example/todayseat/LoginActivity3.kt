package com.example.todayseat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todayseat.databinding.ActivityLogin3Binding

class LoginActivity3 : AppCompatActivity() {

    //처리해야할 데이터
    val meat_score:Int = -1
    val sea_score:Int = -1
    val vege_score:Int = -1

    lateinit var binding:ActivityLogin3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogin3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        // 다음 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, LoginActivity3_2::class.java)
            startActivity(intent)
            finish()
        }

        // 이전 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.beforeButton.setOnClickListener{
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}