package com.example.covid19_vaccin_center.Splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.covid19_vaccin_center.Map.view.MapActivity
import com.example.covid19_vaccin_center.R
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository
import com.example.covid19_vaccin_center.Splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: SplashActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)
        val repository = VaccineRepository(this)
        viewModel = SplashViewModel(repository)
        viewModel.fetchVaccines()

        updateProgressBar()
    }
    private fun updateProgressBar() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            var progress = 0

            override fun run() {
                if (progress < 80) {
                    progress += 5
                }
                progressBar.progress = progress

                if (progress >= 100) {
                    // MAP으로 이동
                    startActivity(Intent(this@SplashActivity, MapActivity::class.java))
                    finish()
                } else {
                    handler.postDelayed(this, 200)
                }
            }
        }

        handler.post(runnable)

        // 대기
        lifecycleScope.launch {
            delay(2000)
            handler.removeCallbacks(runnable)
            progressBar.progress = 80

            // 대기
            delay(400)
            progressBar.progress = 100

            /*데이터가 잘 저장되어있는지 확인 하는 부분
            val vaccines = viewModel.getAllVaccines()
            for (vaccine in vaccines) {
                Log.e("Vaccine","printVaccineData 실행")
                Log.e("Vaccine","printVaccineData 실행 주소 ${vaccine.address}")
                Log.e("Vaccine","printVaccineData 실행 센터 네임 ${vaccine.centerName}")
                Log.e("Vaccine","printVaccineData 실행 lat ${vaccine.lat}")
                Log.e("Vaccine","printVaccineData 실행 lng ${vaccine.lng}")
            }
            -> 필요는 없음*/


            // MAP으로 이동
             startActivity(Intent(this@SplashActivity, MapActivity::class.java))
        }
    }
}
