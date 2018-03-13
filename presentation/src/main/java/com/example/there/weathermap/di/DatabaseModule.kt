package com.example.there.weathermap.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.there.data.WeatherMapDatabase
import com.example.there.data.city.CityDao
import com.example.there.data.city.CityMapper
import com.example.there.data.city.CityRepository
import com.example.there.domain.city.BaseCityRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(app: Application) {
    private val database: WeatherMapDatabase =  Room.databaseBuilder(app.applicationContext, WeatherMapDatabase::class.java, "WeatherMap.db").build()

    @Provides
    @Singleton
    fun weatherMapDatabase(): WeatherMapDatabase = database

    @Provides
    @Singleton
    fun cityDao(): CityDao = database.cityDao()

    @Provides
    @Singleton
    fun cityMapper(): CityMapper = CityMapper()

    @Provides
    @Singleton
    fun cityRepository(mapper: CityMapper, dao: CityDao): BaseCityRepository = CityRepository(mapper, dao)
}