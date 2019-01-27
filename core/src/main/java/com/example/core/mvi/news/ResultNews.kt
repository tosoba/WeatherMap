package com.example.core.mvi.news

sealed class ResultNews {
    object StartedLoading : ResultNews()
    data class Loaded<Result : Any>(val result: Result) : ResultNews()
    data class ErrorLoading(val throwable: Throwable) : ResultNews()
}