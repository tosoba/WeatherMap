package com.example.there.weathermap.di

import com.example.there.weathermap.WeatherMapApp
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
    ApiModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherMapApp): Builder

        fun appModule(appModule: AppModule): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder

        fun apiModule(apiModule: ApiModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherMapApp)
}