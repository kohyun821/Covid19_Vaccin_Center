package com.example.covid19_vaccin_center.Map.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.covid19_vaccin_center.Splash.data.dao.VaccineDao
import com.example.covid19_vaccin_center.Splash.data.database.VaccineDatabase
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository

class MapViewModel(private val repository: VaccineRepository) : ViewModel() {

    fun getAllVaccines(): LiveData<List<Vaccine>> {
        return repository.getAllVaccines().asLiveData()
    }
}