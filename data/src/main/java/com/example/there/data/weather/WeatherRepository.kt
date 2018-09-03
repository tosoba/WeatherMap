package com.example.there.data.weather

import com.example.there.domain.weather.BaseWeatherRepository
import com.example.there.domain.weather.Weather
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class WeatherRepository @Inject constructor(
        private val service: WeatherService
): BaseWeatherRepository {
    override fun loadWeather(
            lat: Double,
            lon: Double
    ): Single<Weather> = service.loadWeather(position = "$lat,$lon").map(WeatherMapper::fromEntity)
}