package com.example.there.data.weather

data class WeatherCurrentlyEntity(
        val time: Long,
        val summary: String,
        val icon: String,
        val nearestStormDistance: Double,
        val nearestStormBearing: Int,
        val precipIntensity: Double,
        val precipProbability: Double,
        val temperature: Double,
        val apparentTemperature: Double,
        val dewPoint: Double,
        val humidity: Double,
        val pressure: Double,
        val windSpeed: Double,
        val windGust: Double,
        val windBearing: Int,
        val cloudCover: Double,
        val uvIndex: Double,
        val visibility: Double,
        val ozone: Double
)