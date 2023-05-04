package com.example.covid19_vaccin_center.Splash.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.covid19_vaccin_center.Splash.data.dao.VaccineDao
import com.example.covid19_vaccin_center.Splash.data.entity.Vaccine

@Database(entities = [Vaccine::class], version = 2)
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
                ).addMigrations(migration_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE vaccine Add COLUMN centerType TEXT NOT NULL DEFAULT ''"
        )
    }

}