package com.example.core.mapper

interface TwoWayMapper<E, M>: OneWayMapper<E, M> {
    fun toEntity(model: M): E
}