package com.example.there.domain.base

interface OneWayMapper<in E, out M> {
    fun fromEntity(entity: E): M
}