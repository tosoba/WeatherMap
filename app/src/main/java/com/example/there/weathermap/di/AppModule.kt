package com.example.there.weathermap.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.core.di.ActivityScope
import com.example.coreandroid.di.ViewModelKey
import com.example.coreandroid.routing.Router
import com.example.coreandroid.rx.AsyncCompletableTransformer
import com.example.coreandroid.rx.AsyncSymmetricObservableTransformer
import com.example.map.data.MapRepository
import com.example.map.di.MapModule
import com.example.map.domain.feature.FindCitiesInBoundsFeature
import com.example.map.domain.feature.LoadWeatherForCitiesFeature
import com.example.map.domain.repo.IMapRepository
import com.example.map.ui.MapActivity
import com.example.map.ui.MapViewModel
import com.example.splash.data.SplashRepository
import com.example.splash.di.SplashModule
import com.example.splash.domain.feature.LoadCitiesDataFeature
import com.example.splash.domain.repo.ISplashRepository
import com.example.splash.ui.SplashActivity
import com.example.splash.ui.SplashViewModel
import com.example.there.weathermap.routing.AppRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {

    @Binds
    abstract fun applicationContext(application: Application): Context

    @Binds
    abstract fun router(appRouter: AppRouter): Router

    @ActivityScope
    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun bindMapActivity(): MapActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    abstract fun splashRepository(repository: SplashRepository): ISplashRepository

    @Binds
    abstract fun mapRepository(repository: MapRepository): IMapRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun findCitiesInBoundsFeature(
                repository: IMapRepository
        ): FindCitiesInBoundsFeature = FindCitiesInBoundsFeature(repository, AsyncSymmetricObservableTransformer())

        @Provides
        @JvmStatic
        fun loadWeatherForCitiesFeature(
                repository: IMapRepository
        ): LoadWeatherForCitiesFeature = LoadWeatherForCitiesFeature(repository, AsyncSymmetricObservableTransformer())

        @Provides
        @JvmStatic
        fun loadCitiesDataFeature(
                repository: ISplashRepository
        ): LoadCitiesDataFeature = LoadCitiesDataFeature(repository, AsyncCompletableTransformer())

    }
}