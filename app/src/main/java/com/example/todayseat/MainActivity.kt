package com.example.todayseat

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todayseat.ui.home.HomeFragment
import com.example.todayseat.ui.myPage.MyPageFragment
import com.example.todayseat.ui.preference.PreferenceFragment

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Log.d("LifeCycleTest","onCreate")
       // loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.nav_view) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    Log.d("clickTest","homeclick!")
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
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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