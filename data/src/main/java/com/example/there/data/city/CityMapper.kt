package com.example.there.data.city

import com.example.there.domain.base.TwoWayMapper
import com.example.there.domain.city.City

class CityMapper: TwoWayMapper<CityEntity, City> {
    override fun fromEntity(entity: CityEntity): City = City(entity.lat, entity.lon, entity.name, entity.population)

    override fun toEntity(model: City): CityEntity = CityEntity(model.lat, model.lon, model.name, model.population)
}