package com.example.there.domain.base

interface TwoWayMapper<E, M>: OneWayMapper<E, M> {
    fun toEntity(model: M): E
}