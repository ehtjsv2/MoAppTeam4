package com.example.todayseat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed

class SplashActivity : AppCompatActivity() {
    private val splashDuration = 3000L

    /*
    // 처리해야할 데이터 login 1
    var name:String = "-"
    var age:Int = 0
    var stature:Int = 0
    var gender:String = "-"

    //처리해야할 데이터 login2
    var activity_index:Int = -1

    //처리해야할 데이터 login3
     val meat_score:Int = -1
    val sea_score:Int = -1
    val vege_score:Int = -1
    val noodle_score:Int = -1

    //처리해야할 데이터 3_2
    val snackbar_score:Int = -1
    val rice_score:Int = -1
    val korean_score:Int = -1
    val etc_score:Int = -1

    //처리해야할 데이터 login4
    var like_list:MutableList<String> = ArrayList()

    //처리해야할 데이터 login5
    val dislike_list:MutableList<String> = ArrayList()
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler()를 통해서 UI 쓰레드를 컨트롤 한다.
        // Handler().postDelayed(딜레이 시간){딜레이 이후 동작}
        //      postDelayed()를 통해 일정 시간(딜레이 시간)동안 쓰레드 작업을 멈춘다.
        //      {딜레이 이후 동작}을 통해 딜레이 시간 이후, 동작을 정의해준다.

        // 정용 : 초기 데이터를 SharedPreferences를 이용해 가지고 있다가 데이터 여부에 따라
        // 바로 메인으로 갈지 Login으로 갈지 설정한다.

        Handler().postDelayed(splashDuration){
            //val intent = Intent(this, MainActivity::class.java)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}