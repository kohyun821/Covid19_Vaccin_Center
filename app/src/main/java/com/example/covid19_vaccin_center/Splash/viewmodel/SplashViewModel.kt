package com.example.covid19_vaccin_center.Splash.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid19_vaccin_center.Splash.data.dto.RetrofitObject
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val repository: VaccineRepository) : ViewModel() {

    fun fetchVaccines() {
        viewModelScope.launch {
            for (page in 1..10) {
                withContext(Dispatchers.IO){
                    val call = RetrofitObject.getApiService()
                        .getInfo(page = page, perPage = 10)

                    val response = call.execute()

                    if (response.isSuccessful) {
                        val vaccines = response.body()?.data ?: emptyList()
                        repository.insertVaccines(vaccines)
                    } else {
                        println("com.example.covid19_vaccin_center 실행 실패")
                        Log.e("Vaccine","fetchVaccines의 error 실행")
                    }
                }
            }
        }
    }
/*데이터가 잘 저장되어있는지 확인 하는 부분
suspend fun getAllVaccines(): List<Vaccine> {
return repository.getAllVaccines()
}
-> 필요는 없음*/

}
