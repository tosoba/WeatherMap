package com.example.api.mapper

import com.example.api.entity.WeatherEntity
import com.example.core.model.Weather

object WeatherMapper: com.example.core.mapper.OneWayMapper<WeatherEntity, Weather> {
    override fun fromEntity(
            entity: WeatherEntity
    ): Weather = Weather(
            toCelsius(entity.currently.temperature).toInt(),
            entity.currently.icon
    )

    private fun toCelsius(temp: Double): Double {
        return 5.0 / 9.0 * (temp - 32.0)
    }
}