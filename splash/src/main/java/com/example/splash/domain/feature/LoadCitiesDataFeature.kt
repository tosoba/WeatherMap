package com.example.splash.domain.feature

import com.example.core.model.City
import com.example.core.mvi.feature.SimpleStatelessAsyncFeature
import com.example.splash.domain.repo.ISplashRepository
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Single
import org.apache.commons.io.IOUtils
import java.io.InputStream

class LoadCitiesDataFeature(
        repository: ISplashRepository,
        transformer: CompletableTransformer
) : SimpleStatelessAsyncFeature<InputStream>(
        actor = SimpleStatelessAsyncFeature.ActorImpl(transformer) {
            repository.isCityDataImported.flatMapCompletable { dataImported ->
                if (dataImported) Completable.complete()
                else Single.fromCallable {
                    val cityDataStr = IOUtils.toString(this)
                    Gson().fromJson<Array<City>>(cityDataStr, Array<City>::class.java).toList()
                }.flatMapCompletable { repository.insertCities(it) }
            }
        }
)