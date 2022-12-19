package com.example.todayseat.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.todayseat.Login.LoginActivity2
import com.example.todayseat.MainActivity
import com.example.todayseat.R
import com.example.todayseat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // 처리해야할 데이터
    var name:String = "-"
    var age:Int = 0
    var stature:Int = 0
    var gender:String = "-"

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 클릭 이벤트 추후 캐시, 값 여부 판단, 데이터 처리 추가하기
        binding.nextButton.setOnClickListener{
            if(binding.joinName.text!!.isEmpty())
            {
                name = "-"
                //Toast.makeText(this,"값을 입력하세요",Toast.LENGTH_SHORT).show()
            }
            else{
                name = binding.joinName.text.toString()
            }

            if(binding.joinAge.text!!.isEmpty())
            {
                age = 0
                //Toast.makeText(this,"값을 입력하세요",Toast.LENGTH_SHORT).show()
            }
            else{
                age = binding.joinAge.text.toString().toInt()
            }

            if(binding.joinStature.text!!.isEmpty())
            {
                stature = 0
                //Toast.makeText(this,"값을 입력하세요",Toast.LENGTH_SHORT).show()
            }
            else{
                stature = binding.joinStature.text.toString().toInt()
            }

            //Log.d("pjy", name)
            //Log.d("pjy", age.toString())
            //Log.d("pjy", stature.toString())
            //Log.d("pjy", gender)


            if(name == "-" || age == 0 || stature == 0 || gender == "-"){
                AlertDialog.Builder(this@LoginActivity).setTitle("모든 문항을 입력해야 합니다")
                    .setCancelable(true).setNegativeButton("확인", null)
                    .show()
            }
            else {
                // 추후 데이터 저장 여기에
                val intent = Intent(this, LoginActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.groupGender.setOnCheckedChangeListener{ Group, CheckedId ->
            when(CheckedId){
                R.id.gender_fm -> {gender = "여자"
                    // 해당 변수를 sqlite에 저장하고 가져와야함
                    //Log.d("pjy", gender)
                }
                R.id.gender_m -> {
                    gender = "남자"
                    //Log.d("pjy", gender)
                }
            }
        }

    }




}