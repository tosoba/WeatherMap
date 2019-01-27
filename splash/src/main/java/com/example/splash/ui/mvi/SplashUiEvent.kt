package com.example.splash.ui.mvi

import com.example.core.mvi.wish.LoadAsyncWish
import java.io.InputStream

sealed class SplashUiEvent {
    class OnCreate(val citiesDataStream: InputStream) : SplashUiEvent()

    object Transformer : (SplashUiEvent) -> LoadAsyncWish<InputStream>? {
        override fun invoke(event: SplashUiEvent): LoadAsyncWish<InputStream>? = when (event) {
            is OnCreate -> LoadAsyncWish(event.citiesDataStream)
        }
    }
}