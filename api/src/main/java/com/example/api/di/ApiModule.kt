package com.example.api.di

import com.example.api.WeatherApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class ApiModule {
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
        fun weatherService(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
    }
}