package com.example.covid19_vaccin_center.Splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.covid19_vaccin_center.Map.view.MapActivity
import com.example.covid19_vaccin_center.R
import com.example.covid19_vaccin_center.Splash.viewmodel.SplashViewModel
import com.example.covid19_vaccin_center.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

//        viewModel.progress.observe(this, Observer { progress ->
//            binding.progressBar.progress = progress
//            if (progress == 100) {
//                val intent = Intent(this, MapActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        })

        viewModel.fetchVaccineData()
    }
}
