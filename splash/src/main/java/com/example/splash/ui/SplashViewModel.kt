package com.example.splash.ui

import android.arch.lifecycle.ViewModel
import com.example.splash.domain.feature.LoadCitiesDataFeature
import javax.inject.Inject

class SplashViewModel @Inject constructor(
        val loadCitiesDataFeature: LoadCitiesDataFeature
) : ViewModel() {

    override fun onCleared() {
        loadCitiesDataFeature.dispose()
        super.onCleared()
    }
}