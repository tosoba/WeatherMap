package com.example.there.domain.city

import io.reactivex.Completable
import io.reactivex.Single

interface BaseCityRepository {
    fun insertMany(cities: List<City>): Completable

    fun findInBounds(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): Single<List<City>>

    fun getNumberOfRecords(): Single<Int>
}