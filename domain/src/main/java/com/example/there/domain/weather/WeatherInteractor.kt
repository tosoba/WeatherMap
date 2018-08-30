package com.example.there.domain.weather

import io.reactivex.Observable
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private val repository: BaseWeatherRepository) {
    fun loadWeather(lat: Double, lon: Double): Observable<Weather> = repository.loadWeather(lat, lon)
}