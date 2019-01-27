package com.example.map.data

import com.example.api.WeatherApi
import com.example.api.mapper.WeatherMapper
import com.example.core.model.City
import com.example.core.model.Weather
import com.example.db.dao.CityDao
import com.example.db.mapper.CityMapper
import com.example.map.domain.repo.IMapRepository
import io.reactivex.Single
import javax.inject.Inject

class MapRepository @Inject constructor(
        private val weatherApi: WeatherApi,
        private val cityDao: CityDao
) : IMapRepository {

    override fun findLargestCitiesInBounds(
            minLat: Double,
            maxLat: Double,
            minLon: Double,
            maxLon: Double
    ): Single<List<City>> = cityDao.findInBounds(minLat, maxLat, minLon, maxLon).map {
        it.map(CityMapper::fromEntity)
    }

    override fun loadWeather(
            lat: Double,
            lon: Double
    ): Single<Weather> = weatherApi.loadWeather(
            position = "$lat,$lon"
    ).map(WeatherMapper::fromEntity)
}