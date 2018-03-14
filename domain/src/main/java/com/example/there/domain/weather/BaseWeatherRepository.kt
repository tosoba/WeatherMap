package com.example.there.domain.weather

import io.reactivex.Completable
import io.reactivex.Observable

interface BaseWeatherRepository {
    fun loadWeather(lat: Double, lon: Double): Observable<Weather>
}