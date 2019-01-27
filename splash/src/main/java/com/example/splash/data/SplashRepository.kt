package com.example.splash.data

import com.example.core.model.City
import com.example.db.dao.CityDao
import com.example.db.mapper.CityMapper
import com.example.splash.domain.repo.ISplashRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class SplashRepository @Inject constructor(
        private val cityDao: CityDao
) : ISplashRepository {

    override val isCityDataImported: Single<Boolean>
        get() = cityDao.getNumberOfRecords().map { it == expectedNumberOfRecords }

    override fun insertCities(cities: List<City>): Completable = Completable.fromCallable {
        cityDao.insertMany(*cities.map(CityMapper::toEntity).toTypedArray())
    }

    companion object {
        private const val expectedNumberOfRecords = 95220
    }
}