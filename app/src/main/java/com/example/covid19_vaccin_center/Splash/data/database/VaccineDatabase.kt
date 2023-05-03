package com.example.covid19_vaccin_center.Splash.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covid19_vaccin_center.Splash.data.dao.VaccineDao
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine

@Database(entities = [Vaccine::class], version = 1)
abstract class VaccineDatabase : RoomDatabase() {
    abstract fun vaccineDao(): VaccineDao

    companion object {
        @Volatile
        private var INSTANCE: VaccineDatabase? = null

        fun getDatabase(context: Context): VaccineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VaccineDatabase::class.java,
                    "vaccine_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
