package com.example.todayseat

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.postDelayed
import com.example.todayseat.Login.LoginActivity
import com.example.todayseat.databinding.ActivitySplashBinding

import com.example.todayseat.ui.home.MapActivity
import com.example.todayseat.ui.home.HomeFragment
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader


class SplashActivity : AppCompatActivity() {
    private val splashDuration = 2000L
//    val pref = this.getPreferences(0)
//    val editor = pref.edit()

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
        //val binding = ActivitySplashBinding.inflate(layoutInflater)

        helper = myDBHelper(this)
        moappDB = helper.writableDatabase
//        helper.onUpgrade(moappDB, 1, 2)
//        val isInstall = pref.getInt("isInstall",0)
//        if(isInstall==1){
//            binding.textView4.visibility= View.INVISIBLE
//        }
        setContentView(R.layout.activity_splash)

        // blink 애니메이션
        val anim = AnimationUtils.loadAnimation(this,R.anim.blink_animation)
        // 애니메이션 재생
        val animTong = AnimationUtils.loadAnimation(this,R.anim.anim_splash_imageview)

        val image : TextView = findViewById(R.id.textView4)
        val image2 : ImageView = findViewById(R.id.imageView5)
        val image3 : ImageView = findViewById(R.id.ohsiki_logo)
        image.startAnimation(anim)
        image2.startAnimation(animTong)
        image3.startAnimation(anim)

        // Handler()를 통해서 UI 쓰레드를 컨트롤 한다.
        // Handler().postDelayed(딜레이 시간){딜레이 이후 동작}
        //      postDelayed()를 통해 일정 시간(딜레이 시간)동안 쓰레드 작업을 멈춘다.
        //      {딜레이 이후 동작}을 통해 딜레이 시간 이후, 동작을 정의해준다.

        // 정용 : 초기 데이터를 sqlite를 이용해 가지고 있다가 데이터 여부에 따라
        // 바로 메인으로 갈지 Login으로 갈지 설정해야합니다. 그 과정을 위해 미리 변수 선언 했습니다.

        Handler().postDelayed(splashDuration){
            val sql = "SELECT check_info FROM CUSTOMER where C_ID=1"
            val c = moappDB.rawQuery(sql,null)
            c.moveToNext()
            val check_value:Int = c.getColumnIndex("check_info")
            val str = c.getInt(check_value)
//            val str = 1
            Log.d("pjy",str.toString())
//            val sql2 = "SELECT RN_kcal FROM RECOMMENDNUTRIENT where RN_ID=1"
//            val c2 = moappDB.rawQuery(sql2,null)
//            c2.moveToNext()
//            var check_value2:Int = c2.getColumnIndex("RN_kcal")
//            var str2=c2.getFloat(check_value2)
           // Log.d("pjy",str2.toString())
            //val intent = Intent(this, MainActivity::class.java)
            if(str == 1){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(str == 0){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    inner class myDBHelper(context: Context) :
        SQLiteOpenHelper(context, "Moapp4", null, 1) {
        //앱이 설치되어서 SQLiteOpenHelper 클래스가 최초로 되는 순간 호출,테이블 생성
        override fun onCreate(db: SQLiteDatabase?) {
            Log.d("DB1234","testCreate")
            //1.CUSTOMER TABLE
            db?.execSQL(
                "CREATE TABLE CUSTOMER(\n" +
                        "C_id VARCHAR(10),\n" +
                        "C_name VARCHAR(20),\n" +
                        "gender VARCHAR(5),\n" +
                        "need_calorie FLOAT,\n" +
                        "favor_category_ID VARCHAR(10),\n" +
                        "N_score_ID VARCHAR(10),\n" +
                        "age INT,\n" +
                        "height FLOAT,\n" +
                        "activation INT,\n" +
                        "check_info BOOLEAN,\n" +
                        "PRIMARY KEY(C_id)\n" +
                        ");"
            )
            //2.FOODCATEGORY TABLE
            db?.execSQL(
                "CREATE TABLE FOODCATEGORY(\n" +
                        "Category_ID VARCHAR(10),\n" +
                        "Category_name VARCHAR(20),\n" +
                        "PRIMARY KEY(Category_ID)\n" +
                        ");"
            )
            //3.FOOD TABLE
            db?.execSQL(
                "CREATE TABLE FOOD(\n" +
                        "F_ID VARCHAR(3),\n" +
                        "F_name VARCHAR(30),\n" +
                        "category_app INT,\n" +
                        "serving_size INT,\n" +
                        "content_unit CHAR,\n" +
                        "kcal FLOAT,\n" +
                        "carbo FLOAT,\n" +
                        "fat FLOAT,\n" +
                        "protein FLOAT,\n" +
                        "preference_check INT,\n"+
                        "PRIMARY KEY(F_ID)\n" +
                        ");"
            )
            //4.FOODFAVOR 테이블
            db?.execSQL(
                "CREATE TABLE FOODFAVOR(\n" +
                        "Favor_ID VARCHAR(10),\n" +
                        "meat INT,\n" +
                        "seafood INT,\n" +
                        "vegetable INT,\n" +
                        "noodle INT,\n" +
                        "snack_bar INT,\n" +
                        "korean INT,\n" +
                        "rice INT,\n" +
                        "etc INT,\n" +
                        "PRIMARY KEY(Favor_id)\n" +
                        ");"
            )
            //5.FOODRECENT 테이블
            db?.execSQL(
                "CREATE TABLE FOODRECENT(\n" +
                        "List_ID integer,\n" +
                        "food_eat_ID VARCHAR(10),\n" +
                        "Date_eat  DATE,\n" +
                        "C_ID_eat VARCHAR(10),\n" +
                        "PRIMARY KEY(List_id)\n" +
                        ");"
            )
            //6.EXCLUDEFOOD 테이블
            db?.execSQL(
                "CREATE TABLE EXCLUDEFOOD(\n" +
                        "Exclude_ID VARCHAR(10),\n" +
                        "C_ID VARCHAR(10),\n" +
                        "F_ID VARCHAR(10),\n" +
                        "PRIMARY KEY(Exclude_id)\n" +
                        ");"
            )
            //7.RECEIPE 테이블
            db?.execSQL(
                "CREATE TABLE RECEIPE(\n" +
                        "R_ID VARCHAR(10),\n" +
                        "R_name VARCHAR(30),\n" +
                        "F_ID VARCHAR(10),\n" +
                        "R_ingredient TEXT,\n" +
                        "R_seasoning TEXT,\n" +
                        "R_step TEXT,\n" +
                        "PRIMARY KEY(R_id)\n" +
                        ");"
            )
            //8.NUTRIENTSCORE 테이블
            db?.execSQL(
                "CREATE TABLE NUTRIENTSCORE(\n" +
                        "S_ID VARCHAR(10),\n" +
                        "score FLOAT,\n" +
                        "S_date DATE,\n" +
                        "PRIMARY KEY(S_ID)\n" +
                        ");"
            )
            //9.PHOTO 테이블
            db?.execSQL(
                "CREATE TABLE PHOTO(\n" +
                        "P_ID VARCHAR(10),\n" +
                        "F_name VARCHAR(30),\n" +
                        "F_url VARCHAR(200),\n" +
                        "PRIMARY KEY(P_ID),\n" +
                        "FOREIGN KEY (P_ID) REFERENCES F_ID(FOOD)\n"+
                        ");"
            )
            //10.권장영양소(RECOMMENDNUTRIENT)테이블
            db?.execSQL("CREATE TABLE RECOMMENDNUTRIENT(\n" + //recommendnutrient
                    "RN_ID VARCHAR(10),\n" +
                    "C_id VARCHAR(10),\n" +
                    "RN_kcal FLOAT,\n" +
                    "RN_carbo FLOAT,\n" +
                    "RN_protein FLOAT,\n" +
                    "RN_fat FLOAT,\n" +
                    "PRIMARY KEY(RN_ID)\n" +
                    ");")

            Log.i("DB1234", "create DB")
            insertCsv_to_DB(db)
            insertReceipeCsv_to_DB(db)
            insertPhotoCsv_to_DB(db)
            Log.i("DB1234", "insertCSV FK in DB")

            var sql="insert into CUSTOMER VALUES ('1',null,null,null,null,null,null,null,null,null);"
            db?.execSQL(sql)
            val sql2="insert into foodfavor VALUES ('1',null,null,null,null,null,null,null,null);"
            db?.execSQL(sql2)
            val sql3= "insert into RECOMMENDNUTRIENT VALUES ('RN_1',1,0,0,0,0);"
            db?.execSQL(sql3)


//            editor.putInt("isInstall", 1);
//            editor.apply()
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//            db?.execSQL("DROP TABLE IF EXISTS CUSTOMER")
//            db?.execSQL("DROP TABLE IF EXISTS FOOD")
//            db?.execSQL("DROP TABLE IF EXISTS FOODCATEGORY")
//            db?.execSQL("DROP TABLE IF EXISTS FOODFAVOR")
//            db?.execSQL("DROP TABLE IF EXISTS FOODRECENT")
//            db?.execSQL("DROP TABLE IF EXISTS EXCLUDEFOOD")
//            db?.execSQL("DROP TABLE IF EXISTS RECEIPE")
//            db?.execSQL("DROP TABLE IF EXISTS NUTRIENTSCORE")
//            db?.execSQL("DROP TABLE IF EXISTS PHOTO")
//            onCreate(db)

        }
    }
    //저장된 csv 파일의 데이터를 DB로 보내는 함수
    //이것도 oncreate 함수내에 넣어서 한번만 시행되도록 하는 것으로 하자
    private fun insertCsv_to_DB(db: SQLiteDatabase?) {
        val assetManager: AssetManager = this.assets
        var k = 0
        var content_str = ""

        val charsToRemove = "[]"
        val inputStream: InputStream = assetManager.open("모앱4팀_음식메뉴전처리본.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
//        : (Mutable)List<Array<out String!>!>!
        val allContent = reader.readAll()

        for (content in allContent) {
            if (k == 0) {
                k = k + 1
                continue
            }
            content_str = content.toList().toString()
            charsToRemove.forEach { content_str = content_str.replace(it.toString(), "") }
            var sql =
                "INSERT INTO FOOD (F_ID, F_name, category_app, serving_size, content_unit \n" +
                        " ,kcal, carbo, fat, protein, preference_check) VALUES (?,?,?,?,?,?,?,?,?,?)"
            val arr = arrayOfNulls<String>(10)
            var a = content_str.split(",")

            for (i in 0..8) {
                arr.set(i, a[i])
            }
            arr.set(9,"0")
            Log.i("DB1234", arr[9].toString())
            db?.execSQL(sql, arr)

        }
        Log.i("DB1234","input Food csvfile to DB end")
    }
    // onCreate 함수내에 넣어서 한번만 시행될 수 있도록 하자
    private fun insertReceipeCsv_to_DB(db: SQLiteDatabase?){
        val assetManager : AssetManager = this.assets
        var k = 0
        var content_str = ""

        val charsToRemove = "[]"
        val inputStream : InputStream = assetManager.open("모앱4팀_음식레시피크롤링본.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
//        : (Mutable)List<Array<out String!>!>!
        val allContent  = reader.readAll()

        for (content in allContent){
            if(k == 0){
                k = k+1
                continue
            }
            content_str = content.toList().toString()
            charsToRemove.forEach{ content_str = content_str.replace(it.toString(),"")}
            var sql = "INSERT INTO RECEIPE (R_ID, R_name, F_ID, R_ingredient, R_seasoning \n"+
                    " ,R_step) VALUES (?,?,?,?,?,?)"
            val arr = arrayOfNulls<String>(6)
            var a = content_str.split(",")

            for (i in 0..5){
                arr.set(i,a[i])
            }

//            Log.i("DB11", content_str)
            db?.execSQL(sql, arr)
        }

//
        Log.i("DB1234","input Receipe csvfile to DB end")
    }
    // onCreate 함수내에 넣어서 한번만 시행될 수 있도록 하자
    private fun insertPhotoCsv_to_DB(db: SQLiteDatabase?){
        val assetManager : AssetManager = this.assets
        var k = 0
        var content_str = ""

        val charsToRemove = "[]"
        val inputStream : InputStream = assetManager.open("모앱4팀_음식사진전처리본.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
//        : (Mutable)List<Array<out String!>!>!
        val allContent  = reader.readAll()

        for (content in allContent){
            if(k == 0){
                k = k+1
                continue
            }
            content_str = content.toList().toString()
            charsToRemove.forEach{ content_str = content_str.replace(it.toString(),"")}
            var sql = "INSERT INTO PHOTO (P_ID, F_name, F_url \n"+
                    " ) VALUES (?,?,?)"
            val arr = arrayOfNulls<String>(3)
            var a = content_str.split(",")

            for (i in 0..2){
                arr.set(i,a[i])
            }

            Log.i("DB112", content_str)
            db?.execSQL(sql, arr)
        }//
        Log.i("DB1234","input PHOTO csvfile to DB end")
    }
    companion object{
        lateinit var helper : SplashActivity.myDBHelper
        lateinit var moappDB : SQLiteDatabase
    }
}