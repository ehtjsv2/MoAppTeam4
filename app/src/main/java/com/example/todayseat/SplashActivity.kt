package com.example.todayseat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.example.todayseat.Login.LoginActivity
import com.example.todayseat.Login.LoginActivity2
import com.example.todayseat.Login.LoginActivity3
import com.example.todayseat.ui.home.MapActivity
import com.example.todayseat.ui.home.HomeFragment


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
     var meat_score:Int = -1
    var sea_score:Int = -1
    var vege_score:Int = -1
    var noodle_score:Int = -1

    //처리해야할 데이터 3_2
    var snack_score:Int = -1
    var rice_score:Int = -1
    var korean_score:Int = -1
    var etc_score:Int = -1

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

        // 정용 : 초기 데이터를 sqllite를 이용해 가지고 있다가 데이터 여부에 따라
        // 바로 메인으로 갈지 Login으로 갈지 설정해야합니다. 그 과정을 위해 미리 변수 선언 했습니다.

        Handler().postDelayed(splashDuration){
            //val intent = Intent(this, MainActivity::class.java)
            val intent = Intent(this, LoginActivity4::class.java)
            startActivity(intent)
            finish()
        }
    }
}