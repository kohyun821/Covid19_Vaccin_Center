package com.example.covid19_vaccin_center.Map.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.covid19_vaccin_center.BuildConfig
import com.example.covid19_vaccin_center.Map.viewmodel.MapViewModel
import com.example.covid19_vaccin_center.R
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository
import com.example.covid19_vaccin_center.databinding.ActivityMapBinding
import com.naver.maps.map.NaverMapSdk

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.CLIENT_ID)

        val repository = VaccineRepository(this)
        viewModel = MapViewModel(repository)

        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용
            showData()
        }

        val thread = Thread(r)
        thread.start()
    }

    private fun showData() {
        val vaccines = viewModel.getAllVaccines()
        for (vaccine in vaccines) {
            Log.e("Vaccine","printVaccineData 실행")
            Log.e("Vaccine","printVaccineData 실행 주소 ${vaccine.address}")
            Log.e("Vaccine","printVaccineData 실행 센터 타입 ${vaccine.centerType}")
            Log.e("Vaccine","printVaccineData 실행 센터 네임 ${vaccine.centerName}")
            Log.e("Vaccine","printVaccineData 실행 lat ${vaccine.lat}")
            Log.e("Vaccine","printVaccineData 실행 lng ${vaccine.lng}")
        }
    }

}

