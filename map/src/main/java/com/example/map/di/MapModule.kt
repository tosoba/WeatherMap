package com.example.map.di

import android.arch.lifecycle.ViewModelProviders
import com.example.coreandroid.di.ViewModelFactory
import com.example.map.ui.MapActivity
import com.example.map.ui.MapViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class MapModule {







    @Module
    companion object {
        @Provides
        @JvmStatic
        fun mapViewModel(
                factory: ViewModelFactory,
                activity: MapActivity
        ): MapViewModel = ViewModelProviders.of(activity, factory).get(MapViewModel::class.java)


    }
}