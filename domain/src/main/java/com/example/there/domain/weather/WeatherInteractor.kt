package com.example.there.domain.weather

import io.reactivex.Observable

class WeatherInteractor(private val repository: BaseWeatherRepository) {
    fun loadWeather(lat: Double, lon: Double): Observable<Weather> = repository.loadWeather(lat, lon)
}