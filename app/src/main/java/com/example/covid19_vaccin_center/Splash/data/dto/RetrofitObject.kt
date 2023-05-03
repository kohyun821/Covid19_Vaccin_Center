package com.example.covid19_vaccin_center.Splash.data.dto

import com.example.covid19_vaccin_center.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject{
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_VACCINE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}