package com.example.db.di

import android.arch.persistence.room.Room
import android.content.Context
import com.example.db.WeatherMapDatabase
import com.example.db.dao.CityDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DbModule {

    @Module
    companion object {
        private const val DB_NAME = "WeatherMap.db"

        @Provides
        @Singleton
        @JvmStatic
        fun weatherMapDatabase(
                applicationContext: Context
        ): WeatherMapDatabase = Room.databaseBuilder(
                applicationContext,
                com.example.db.WeatherMapDatabase::class.java,
                DB_NAME
        ).build()

        @Provides
        @JvmStatic
        fun cityDao(db: WeatherMapDatabase): CityDao = db.cityDao()
    }
}