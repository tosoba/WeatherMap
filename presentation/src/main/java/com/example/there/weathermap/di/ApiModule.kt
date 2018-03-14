package com.example.there.weathermap.di

import com.example.there.data.weather.WeatherMapper
import com.example.there.data.weather.WeatherRepository
import com.example.there.data.weather.WeatherService
import com.example.there.domain.weather.BaseWeatherRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApiModule {
    private val baseApiUrl = "https://api.darksky.net/"

    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

    @Provides
    @Singleton
    fun baseApiUrl(): String = baseApiUrl

    @Provides
    @Singleton
    fun apiRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun weatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun weatherMapper() = WeatherMapper()

    @Provides
    @Singleton
    fun weatherRepository(mapper: WeatherMapper, service: WeatherService): BaseWeatherRepository = WeatherRepository(mapper, service)
}