package com.example.there.weathermap.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.example.there.data.WeatherMapDatabase
import com.example.there.data.city.CityDao
import com.example.there.data.city.CityRepository
import com.example.there.domain.city.BaseCityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DatabaseModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun weatherMapDatabase(applicationContext: Context): WeatherMapDatabase = Room.databaseBuilder(
                applicationContext,
                WeatherMapDatabase::
                class.java,
                "WeatherMap.db"
        ).build()

        @Provides
        @JvmStatic
        fun cityDao(db: WeatherMapDatabase): CityDao = db.cityDao()
    }

    @Binds
    abstract fun cityRepository(repository: CityRepository): BaseCityRepository
}