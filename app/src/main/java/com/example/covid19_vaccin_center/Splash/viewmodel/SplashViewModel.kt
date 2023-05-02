package com.example.covid19_vaccin_center.Splash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covid19_vaccin_center.Splash.data.Vaccine
import com.example.covid19_vaccin_center.Splash.data.retrorit.ApiService
import com.example.covid19_vaccin_center.Splash.data.retrorit.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = RetrofitObject.getApiService()
    val progress = MutableLiveData<Int>()

    init {
        progress.value = 0
    }

    fun fetchVaccineData() {
        Log.d("테스트","뷰모델 매소드 실행 완료")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var currentPage = 1
                val perPage = 10
                val totalPages = 10
                while (currentPage <= totalPages) {
                    try {
                        val response = apiService.getInfo(currentPage, perPage)
                        //TODO
                    // 데이터 읽어오기+ROOM에 데이터 저장하기+프로그래스 바 구현

                        val newProgress = (currentPage * 100 / totalPages) * 0.8
                        progress.postValue(newProgress.toInt())
                    } catch (e: Exception) {
                        Log.e("Error","SplashViewModel -> fetchVaccineData")
                    }
                    currentPage++//다음 페이지
                }

                while (progress.value!! < 80) {
                    delay(100)
                }

                delay(400) // 100%를 위해 0.4초 대기
                progress.postValue(100)
            }
        }
    }
}
