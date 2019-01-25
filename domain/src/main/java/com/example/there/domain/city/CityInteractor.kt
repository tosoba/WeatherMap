package com.example.there.domain.city

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CityInteractor @Inject constructor(
        private val repository: BaseCityRepository
) {
    fun insertMany(cities: List<City>): Completable = repository.insertMany(cities)

    fun findInBounds(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): Single<List<City>> = repository.findInBounds(minLat, maxLat, minLon, maxLon)

    fun isDbComplete(): Single<Boolean> = repository.getNumberOfRecords().map { it == expectedNumberOfRecords }

    fun readCityDataFromFile(data: String): Single<List<City>> = Single.fromCallable {
        Gson().fromJson<Array<City>>(data, Array<City>::class.java).toList()
    }

    companion object {
        private const val expectedNumberOfRecords = 95220
    }
}