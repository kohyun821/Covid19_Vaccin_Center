package com.example.covid19_vaccin_center.Map.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid19_vaccin_center.BuildConfig
import com.example.covid19_vaccin_center.R
import com.naver.maps.map.NaverMapSdk

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.CLIENT_ID)
    }
}