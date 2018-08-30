package com.example.there.data.city

import com.example.there.domain.city.BaseCityRepository
import com.example.there.domain.city.City
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CityRepository @Inject constructor(private val dao: CityDao): BaseCityRepository {
    override fun insertMany(cities: List<City>): Completable = Completable.fromAction {
        dao.insertMany(*cities.map(CityMapper::toEntity).toTypedArray())
    }

    override fun findInBounds(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): Single<List<City>> =
            dao.findInBounds(minLat, maxLat, minLon, maxLon).map { it.map(CityMapper::fromEntity) }

    override fun getNumberOfRecords(): Single<Int> = dao.getNumberOfRecords()
}