package com.example.there.domain.weather

import io.reactivex.Single

interface BaseWeatherRepository {
    fun loadWeather(lat: Double, lon: Double): Single<Weather>
}