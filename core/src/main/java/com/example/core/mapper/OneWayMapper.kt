package com.example.core.mapper

interface OneWayMapper<in E, out M> {
    fun fromEntity(entity: E): M
}