package com.example.todayseat


import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.Context
import android.content.DialogInterface
import android.content.res.AssetManager
import android.database.Cursor
import com.example.todayseat.ui.preference.PreferenceFragment
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AlertDialog
import com.opencsv.CSVReader
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todayseat.SplashActivity.Companion.helper
import com.example.todayseat.SplashActivity.Companion.moappDB
import com.example.todayseat.ui.home.HomeFragment
import com.example.todayseat.ui.home.HomeFragment2
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
        SplashActivity.moappDB.execSQL("update CUSTOMER SET gender = 3 where C_ID=1;")

        val sql = "SELECT gender,C_name FROM customer where C_ID='1';"
        Log.d("TAG11","sql실행")
        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
        while (c.moveToNext()){
            var F_name_pos = c.getColumnIndex("gender")
            val str=c.getString(F_name_pos)
            Log.d("TAG11",str)
        }
        getHashKey()
        setContentView(R.layout.activity_main)
        Log.d("LifeCycleTest", "onCreate")
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
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("종료")
        builder.setMessage("정말로 종료하겠습니까?")
        builder.setPositiveButton("종료") { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        builder.setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }
        builder.show()
    }
}