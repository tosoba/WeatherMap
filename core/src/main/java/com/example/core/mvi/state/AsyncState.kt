package com.example.core.mvi.state

data class AsyncState<Value : Any>(val isLoading: Boolean, val value: Value)