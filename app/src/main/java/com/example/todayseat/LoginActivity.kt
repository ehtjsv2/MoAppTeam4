package com.example.todayseat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.todayseat.databinding.ActivityLoginBinding
import com.example.todayseat.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }


}