package com.example.todayseat


import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.Context
import android.content.res.AssetManager
import com.example.todayseat.ui.preference.PreferenceFragment
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.opencsv.CSVReader
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todayseat.ui.home.HomeFragment
import com.example.todayseat.ui.home.RecipeFragment
import com.example.todayseat.ui.myPage.MyPageFragment
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Signature
import java.io.InputStream
import java.io.InputStreamReader



class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHashKey()
        setContentView(R.layout.activity_main)
        Log.d("LifeCycleTest", "onCreate")
        val helper = myDBHelper(this)
        val moappDB = helper.writableDatabase
        helper.onUpgrade(moappDB, 1, 2)

        insertCsv_to_DB(moappDB)
        insertReceipeCsv_to_DB(moappDB)

        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.nav_view) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    Log.d("clickTest", "homeclick!")
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_preference -> {
                    loadFragment(PreferenceFragment())
                    return@setOnItemSelectedListener true

                }
                R.id.navigation_my_page -> {

                    Log.d("clickTest","friendclick!")
                    loadFragment(MyPageFragment())

                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    private fun loadFragment(fragment: Fragment) {
        Log.d("clickTest", "click!->" + fragment.tag)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //test test
    }

    inner class myDBHelper(context: Context) :
        SQLiteOpenHelper(context, "Moapp4", null, 1) {
        //앱이 설치되어서 SQLiteOpenHelper 클래스가 최초로 되는 순간 호출,테이블 생성
        override fun onCreate(db: SQLiteDatabase?) {
            //1.CUSTOMER TABLE
            db?.execSQL(
                "CREATE TABLE CUSTOMER(\n" +
                        "C_id VARCHAR(10),\n" +
                        "C_name VARCHAR(20),\n" +
                        "gender CHAR(1),\n" +
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
                        "List_ID VARCHAR(10),\n" +
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
            Log.d("DB", "create DB")

            Log.d("DB", "alter FK in DB")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS CUSTOMER")
            db?.execSQL("DROP TABLE IF EXISTS FOOD")
            db?.execSQL("DROP TABLE IF EXISTS FOODCATEGORY")
            db?.execSQL("DROP TABLE IF EXISTS FOODFAVOR")
            db?.execSQL("DROP TABLE IF EXISTS FOODRECENT")
            db?.execSQL("DROP TABLE IF EXISTS EXCLUDEFOOD")
            db?.execSQL("DROP TABLE IF EXISTS RECEIPE")
            db?.execSQL("DROP TABLE IF EXISTS NUTRIENTSCORE")
            onCreate(db)
            Log.d("DB", "OldVersion : ${oldVersion}, newversion  ${newVersion}")
        }
    }

    //저장된 csv 파일의 데이터를 DB로 보내는 함수
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
                        " ,kcal, carbo, fat, protein) VALUES (?,?,?,?,?,?,?,?,?)"
            val arr = arrayOfNulls<String>(9)
            var a = content_str.split(",")

            for (i in 0..8) {
                arr.set(i, a[i])
            }
            Log.i("DB", content_str)
            db?.execSQL(sql, arr)

        }
        Log.i("DB","input Food csvfile to DB end")
    }
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

            Log.i("DB", content_str)
            db?.execSQL(sql, arr)
        }

//
        Log.i("DB","input Receipe csvfile to DB end")
    }

    fun getHashKey() {
        var packageInfo: PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        for (signature: android.content.pm.Signature in packageInfo.signatures) {
            try {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }
}

//val navController = findNavController(R.id.nav_host_fragment_activity_main2)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_preference, R.id.navigation_my_page
//            )
//        )