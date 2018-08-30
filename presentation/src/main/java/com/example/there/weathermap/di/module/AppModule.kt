package com.example.there.weathermap.di.module

import android.app.Application
import android.content.Context
import com.example.there.data.city.CityRepository
import com.example.there.domain.city.BaseCityRepository
import com.example.there.domain.city.CityInteractor
import com.example.there.domain.weather.BaseWeatherRepository
import com.example.there.domain.weather.WeatherInteractor
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}