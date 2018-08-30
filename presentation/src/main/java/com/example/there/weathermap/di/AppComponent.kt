package com.example.there.weathermap.di

import android.app.Application
import com.example.there.weathermap.WeatherMapApp
import com.example.there.weathermap.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildersModule::class,
    DatabaseModule::class,
    ApiModule::class,
    PresentationModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherMapApp)
}