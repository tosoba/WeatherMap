package com.example.db.mapper

import com.example.core.mapper.TwoWayMapper
import com.example.core.model.City
import com.example.db.entity.CityEntity


object CityMapper : TwoWayMapper<CityEntity, City> {
    override fun fromEntity(
            entity: CityEntity
    ): City = City(entity.lat, entity.lon, entity.name, entity.population)

    override fun toEntity(
            model: City
    ): CityEntity = CityEntity(model.lat, model.lon, model.name, model.population)
}