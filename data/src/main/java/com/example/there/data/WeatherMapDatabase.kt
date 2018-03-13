package com.example.there.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.there.data.city.CityDao
import com.example.there.data.city.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class WeatherMapDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}