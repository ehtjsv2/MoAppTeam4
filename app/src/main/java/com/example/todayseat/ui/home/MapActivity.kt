package com.example.todayseat.ui.home

import retrofit2.converter.gson.GsonConverterFactory
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.os.postDelayed
import com.example.todayseat.Login.LoginActivity2
import com.example.todayseat.MainActivity
import com.example.todayseat.R
import com.example.todayseat.databinding.ActivityMainBinding
import com.example.todayseat.databinding.ActivityMapBinding
import com.example.todayseat.databinding.BalloonLayoutBinding
//import com.example.todayseat.ui.home.MapActivity.Companion.API_KEY
//import com.example.todayseat.ui.home.MapActivity.Companion.BASE_URL
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONObject
import retrofit2.*
import java.util.*

interface loadcComplete{
    fun loadComplete(result:ResultSearchKeyword)}


class MapActivity : AppCompatActivity(),loadcComplete {
    lateinit var binding: ActivityMapBinding
    lateinit var jsonresult: ResultSearchKeyword
    var datasize: Int = 0
    var namelist: MutableList<String> = ArrayList() // 장소명, 업체명
    var xlist: MutableList<String> = ArrayList() // x좌표
    var ylist: MutableList<String> = ArrayList() // y좌표
    var addresslist: MutableList<String> = ArrayList() // 지번주소
    var roadlist: MutableList<String> = ArrayList() // 도로명주소
//    private val eventListener = MarkerEventListener(this)

    //    companion object{
//        const val BASE_URL = "https://dapi.kakao.com/"
//        const val API_KEY = "KakaoAK 0825c83724d518a31e8abcaaeb68e9bd"
//    }
//
    override fun loadComplete(result: ResultSearchKeyword) {
//        jsonresult = result
//
//        //Log.d("Log.d",result!!.documents[0].x)
//        Log.d("good",(result!!.documents.size).toString())

        datasize = jsonresult!!.documents.size - 1
        namelist.clear()
        xlist.clear()
        ylist.clear()
        addresslist.clear()
        roadlist.clear()
//
//        for( i in 0..datasize){
//            namelist.add(jsonresult!!.documents[i].place_name)
//            xlist.add(jsonresult!!.documents[i].x)
//            ylist.add(jsonresult!!.documents[i].y)
//            addresslist.add(jsonresult!!.documents[i].address_name)
//            roadlist.add(jsonresult!!.documents[i].road_address_name)
//        }
    }
}
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        binding = ActivityMapBinding.inflate(layoutInflater)
//        val view = binding.root
//
//
//        setContentView(view)
//
//        val map_page_ok_btn:Button = findViewById(com.example.todayseat.R.id.map_page_ok_btn)
//
//        map_page_ok_btn.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//
//        val mapView = MapView(this)
//        val mapViewContainer = findViewById<View>(com.example.todayseat.R.id.map_view) as ViewGroup
//        mapViewContainer.addView(mapView)// 맵띄우기
//        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))  // 커스텀 말풍선 등록
//        mapView.setPOIItemEventListener(eventListener)
//        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//
//        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            val lm: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//            try {
//                val userNowLocation: Location =
//                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
//                val uLatitude = userNowLocation.latitude // 위도 y
//                val uLongitude = userNowLocation.longitude // 경도 x
//                Log.d("test",uLatitude.toString())
//                Log.d("test",uLongitude.toString())
//                val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
//                mapView.setMapCenterPoint(uNowPosition, true) // 지정한 위치로 가기
//                mapView.setZoomLevel(4, true); // 줌레벨 변경
//
//                //searchKeyword("치킨",this@MapActivity,uLongitude,uLongitude)
//                searchKeyword("치킨",this@MapActivity) //나온 결과를 해당 위치에 기입
//
//                val handler = android.os.Handler()
//                handler.postDelayed({
//                    val marker = MapPOIItem()
//                    for (i in 0..datasize) {
//                        marker.apply {
//                            itemName = namelist[i].plus("*".plus(addresslist[i].plus("*".plus(roadlist[i]))))
//                            mapPoint = MapPoint.mapPointWithGeoCoord(
//                                ylist[i].toDouble(),
//                                xlist[i].toDouble()
//                            )   // 좌표
//                            markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
//                            customImageResourceId = R.drawable.map_marker_icon               // 커스텀 마커 이미지
//                            selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
//                            customSelectedImageResourceId = R.drawable.map_marker_icon   // 클릭 시 커스텀 마커 이미지
//                            isCustomImageAutoscale = false      // 커스텀 마커 이미지 크기 자동 조정
//                            setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
//                            mapView.addPOIItem(marker) // 마커찍기
//                        }
//                    }
//                },1000)
//
//
//            }catch(e: NullPointerException){
//                Log.e("LOCATION_ERROR", e.toString())
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ActivityCompat.finishAffinity(this)
//                }else{
//                    ActivityCompat.finishAffinity(this)
//                }
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                System.exit(0)
//            }
//        }else{
//            Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
//
//            // 권한 물어보기
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION),
//                1
//            )
//
//
//        }
//
//
//    }
//    //fun searchKeyword(keyword: String,mCallback:MapActivity,x:String, y:String)
//    fun searchKeyword(keyword: String,mCallback:MapActivity) {
//
//        val retrofit = Retrofit.Builder() // Retrofit 구성
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
//        val call = api.getSearchKeyword(API_KEY, keyword, "FD6") // 검색 조건 입력
//        //val call = api.getSearchKeyword(API_KEY, keyword, "FD6",x,y,1000)
//        // API 서버에 요청
//        call.enqueue(object: Callback<ResultSearchKeyword> {
//            override fun onResponse(
//                call: Call<ResultSearchKeyword>,
//                response: Response<ResultSearchKeyword>
//            ) {
//
//
//                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
//                Log.d("Test", "Raw: ${response.raw()}")
//                Log.d("Test", "Body: ${response.body()}")
//
//                mCallback.loadComplete(response.body()!!)
//
//            }
//
//            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
//                // 통신 실패
//                Log.w("MainActivity", "통신 실패: ${t.message}")
//            }
//        })
//
//    }
//
//
//    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
//        var mCalloutBalloon:BalloonLayoutBinding = BalloonLayoutBinding.inflate(inflater)
//        val name: TextView = mCalloutBalloon.ballTvName
//        val address: TextView = mCalloutBalloon.ballTvAddress
//        val address2: TextView = mCalloutBalloon.textView
//
//
//        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
//            // 마커 클릭 시 나오는 말풍선
//            var split = poiItem!!.itemName.split("*")
//            name.text = split[0]   // 해당 마커의 정보 이용 가능
//            address.text = split[1]
//            address2.text = split[2]
//            return mCalloutBalloon.root
//        }
//
//        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
//            // 말풍선 클릭 시
//            return mCalloutBalloon.root
//        }
//    }
//
//    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {
//        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
//            mapView?.removePOIItem(poiItem)
//        }

//        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
//
//        }
//
//        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
//            // 말풍선 클릭 시
//            mapView?.removePOIItem(poiItem)
//        }
//
//        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
//
//        }
    //}

    /* 내위치기반 검색, kakaoAPI 바꿔줘야함
    private fun searchKeyword2(keyword: String,x:String,y:String) {

        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
        // val call = api.getSearchKeyword(API_KEY, keyword, "FD6",y,x,20000) // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "Raw: ${response.raw()}")
                Log.d("Test", "Body: ${response.body()}")

                var result:ResultSearchKeyword? = response.body()
                //Log.d("Log.d",result!!.documents[0].x)
                Log.d("Test",(result!!.documents.size).toString())

                for( i in 0..result!!.documents.size-1){
                    Log.d("Test",result!!.documents[i].x)
                    Log.d("Test",result!!.documents[i].y)
                }


            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }

     */


    /* 지도관련
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.todayseat.R.layout.activity_map)

        val mapView = MapView(this)
        val mapViewContainer = findViewById<View>(com.example.todayseat.R.id.map_view) as ViewGroup
        mapViewContainer.addView(mapView)

        // 버튼 누르면 현재 위치로 전환
        val map_page_location_btn:Button = findViewById(com.example.todayseat.R.id.map_page_location_btn)

        map_page_location_btn.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                try {
                    val userNowLocation: Location =
                        lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    val uLatitude = userNowLocation.latitude
                    val uLongitude = userNowLocation.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                    mapView.setMapCenterPoint(uNowPosition, true) // 지정한 위치로 가기
                }catch(e: NullPointerException){
                    Log.e("LOCATION_ERROR", e.toString())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.finishAffinity(this)
                    }else{
                        ActivityCompat.finishAffinity(this)
                    }

                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                    System.exit(0)
                }
            }else{
                Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
            }
        }


    }
    */

