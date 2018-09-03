package com.example.there.domain.weather

import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherInteractor @Inject constructor(
        private val repository: BaseWeatherRepository
) {
    fun loadWeather(
            lat: Double,
            lon: Double
    ): Single<Weather> = repository.loadWeather(lat, lon)
}