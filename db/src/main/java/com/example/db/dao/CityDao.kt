package com.example.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.coreandroid.db.dao.BaseDao
import com.example.db.entity.CityEntity
import io.reactivex.Single

@Dao
interface CityDao : BaseDao<CityEntity> {
    @Query("SELECT * FROM cities " +
            "WHERE lat > :minLat AND lat < :maxLat AND lon > :minLon AND lon < :maxLon " +
            "ORDER BY population DESC " +
            "LIMIT 5")
    fun findInBounds(minLat: Double, maxLat: Double, minLon: Double, maxLon: Double): Single<List<com.example.db.entity.CityEntity>>

    @Query("SELECT COUNT(*) FROM cities")
    fun getNumberOfRecords(): Single<Int>
}