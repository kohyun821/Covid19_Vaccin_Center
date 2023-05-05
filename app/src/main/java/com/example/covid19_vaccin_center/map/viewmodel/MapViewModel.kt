package com.example.covid19_vaccin_center.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository

class MapViewModel(private val repository: VaccineRepository) : ViewModel() {

    private val _locationInfo = MutableLiveData<String>()
    val locationInfo: LiveData<String>
        get() = _locationInfo
    fun getAllVaccines(): LiveData<List<Vaccine>> {
        return repository.getAllVaccines().asLiveData()
    }

    fun updateLocationInfo(vaccine: Vaccine) {
        val info = "주소 : ${vaccine.address}\n센터 : ${vaccine.centerName}\n시설 : ${vaccine.facilityName}\n번호 : ${vaccine.phoneNumber}\n날짜 : ${vaccine.updatedAt}"
        _locationInfo.value = info
    }
}


