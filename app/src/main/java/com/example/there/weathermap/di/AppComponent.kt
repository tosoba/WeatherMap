package com.example.there.weathermap.di

import android.app.Application
import com.example.api.di.ApiModule
import com.example.db.di.DbModule
import com.example.there.weathermap.WeatherMapApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    DbModule::class,
    ApiModule::class,
    AppModule::class
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