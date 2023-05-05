package com.example.covid19_vaccin_center.Splash.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccineDao {
    //그냥 덮어버리기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccines(vaccines: List<Vaccine>)

    @Query("SELECT * FROM vaccine LIMIT 100")
    fun getAllVaccines(): Flow<List<Vaccine>>
}