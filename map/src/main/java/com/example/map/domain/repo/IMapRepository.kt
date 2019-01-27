package com.example.map.domain.repo

import com.example.core.model.City
import com.example.core.model.Weather
import io.reactivex.Single

interface IMapRepository {

    fun findLargestCitiesInBounds(
            minLat: Double,
            maxLat: Double,
            minLon: Double,
            maxLon: Double
    ): Single<List<City>>

    fun loadWeather(lat: Double, lon: Double): Single<Weather>
}