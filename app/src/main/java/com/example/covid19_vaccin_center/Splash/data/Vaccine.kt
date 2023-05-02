package com.example.covid19_vaccin_center.Splash.data

import com.google.gson.annotations.SerializedName

data class Vaccine(
    @SerializedName("centerName") val centerName:String,        //예방 접종 센터 명
    @SerializedName("address") val address:String,              //주소
    @SerializedName("facilityName") val facilityName:String,    //시설명
    @SerializedName("phoneNumber") val phoneNumber:String,      //사무실 전화번호
    @SerializedName("updatedAt") val updatedAt:String,          //업데이트 날
    @SerializedName("lat") val lat:String,                      //위도
    @SerializedName("lng") val lng:String                       //경도
)

data class VaccineBody(
    @SerializedName("page") val page:Int,                   //데이터 페이지
    @SerializedName("perPage") val perPage:Int,             //불러올 데이터
    @SerializedName("totalCount") val totalCount:Int,       //데이터 갯수
    @SerializedName("currentCount") val currentCount:Int,   //검색된 데이터 수
    @SerializedName("matchCount") val matchCount:Int        //검색과 일치하는 데이터 수
)