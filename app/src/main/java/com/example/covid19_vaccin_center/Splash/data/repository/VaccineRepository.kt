package com.example.covid19_vaccin_center.Splash.data.repository

import android.content.Context
import com.example.covid19_vaccin_center.Splash.data.database.VaccineDatabase
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine

class VaccineRepository(private val context: Context) {
    private val vaccineDao = VaccineDatabase.getDatabase(context).vaccineDao()

    suspend fun insertVaccines(vaccines: List<Vaccine>) {
        vaccineDao.insertVaccines(vaccines)
    }

    suspend fun getAllVaccines(): List<Vaccine> {
        return vaccineDao.getAllVaccines()
    }
}
