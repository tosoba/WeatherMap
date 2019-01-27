package com.example.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.db.dao.CityDao
import com.example.db.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class WeatherMapDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}