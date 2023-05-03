package com.example.covid19_vaccin_center.Splash.data.dto

import com.example.covid19_vaccin_center.BuildConfig
import com.example.covid19_vaccin_center.Splash.data.entity.VaccineBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.ENDPOINT_GET_VACCINE_STATUS)
    fun getInfo(
        @Query("page")page:Int,                             //데이터 페이지
        @Query("perPage")perPage:Int,                       //불러올 데이터
        @Query("serviceKey")ServiceKey:String = BuildConfig.DECODING_KEY,             //Decoding Key
        @Header("accept") Accept: String = BuildConfig.HEADER_ACCEPT,                 //application/1json
        @Header("Authorization") Authorization: String = BuildConfig.ENCODING_KEY     //Encoding Key
    ): Call<VaccineBody>
}