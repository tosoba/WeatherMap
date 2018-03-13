package com.example.there.weathermap.di

import android.app.Application
import android.content.Context
import com.example.there.data.city.CityRepository
import com.example.there.domain.city.BaseCityRepository
import com.example.there.domain.city.CityInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Singleton
    @Provides
    fun application(): Application = app

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun cityInteractor(repository: BaseCityRepository): CityInteractor = CityInteractor(repository)
}