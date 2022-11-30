package com.example.todayseat.ui.home

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("v2/local/search/keyword.json?y=35.8908115062221&x=128.612916813071&radius=1000") // Keyword.json의 정보를 받아옴
    //@GET("v2/local/search/keyword.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키 [필수]
        @Query("query") query: String, // 검색을 원하는 질의어 [필수]
// 매개변수 추가 가능
        @Query("category_group_code") category: String,
       // @Query("y") y: String,
       // @Query("x") x: String,
        // @Query("x") radius:Int
// 주석처리된 부분 내 위치기반
    ): Call<ResultSearchKeyword> // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}