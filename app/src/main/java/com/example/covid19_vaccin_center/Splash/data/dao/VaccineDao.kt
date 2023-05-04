package com.example.covid19_vaccin_center.Splash.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine

@Dao
interface VaccineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccines(vaccines: List<Vaccine>)
}
