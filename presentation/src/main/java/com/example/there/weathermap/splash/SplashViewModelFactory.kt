package com.example.there.weathermap.splash

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.there.domain.city.CityInteractor

class SplashViewModelFactory(private val interactor: CityInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(interactor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}