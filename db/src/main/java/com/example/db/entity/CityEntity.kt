package com.example.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

@Entity(
        tableName = "cities",
        primaryKeys = ["lat", "lon"],
        indices = [
            Index("population"),
            Index("lat"),
            Index("lon")
        ]
)
data class CityEntity(
        @ColumnInfo(name = "lat")
        val lat: Double,

        @ColumnInfo(name = "lon")
        val lon: Double,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "population")
        val population: Int
)