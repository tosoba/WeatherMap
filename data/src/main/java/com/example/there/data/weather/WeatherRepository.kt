package com.example.there.data.weather

import com.example.there.domain.weather.BaseWeatherRepository
import com.example.there.domain.weather.Weather
import io.reactivex.Observable
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val service: WeatherService): BaseWeatherRepository {
    override fun loadWeather(
            lat: Double,
            lon: Double
    ): Observable<Weather> = service.loadWeather(position = "$lat,$lon").map(WeatherMapper::fromEntity)
}