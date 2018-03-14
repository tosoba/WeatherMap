package com.example.there.data.weather

import com.example.there.domain.base.OneWayMapper
import com.example.there.domain.weather.Weather

class WeatherMapper: OneWayMapper<WeatherEntity, Weather> {
    override fun fromEntity(entity: WeatherEntity): Weather = Weather(toCelsius(entity.currently.temperature).toInt(), entity.currently.icon)

    private fun toCelsius(temp: Double): Double {
        return 5.0 / 9.0 * (temp - 32.0)
    }
}