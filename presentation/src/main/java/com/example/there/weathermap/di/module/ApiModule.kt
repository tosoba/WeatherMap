package com.example.there.weathermap.di.module

import com.example.there.data.weather.WeatherRepository
import com.example.there.data.weather.WeatherService
import com.example.there.domain.weather.BaseWeatherRepository
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
abstract class ApiModule {

    @Binds
    abstract fun weatherRepository(repository: WeatherRepository): BaseWeatherRepository

    @Module
    companion object {
        private const val baseApiUrl = "https://api.darksky.net/"

        @Provides
        @JvmStatic
        fun apiRetrofit(): Retrofit = Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()))
                .build()

        @Provides
        @JvmStatic
        fun weatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)
    }
}