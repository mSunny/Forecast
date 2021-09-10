package com.example.forecast.domain

import com.example.forecast.data.db.Location
import com.example.forecast.data.network.ForecastRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetPossibleLocationsInteractor @Inject constructor(private val forecastRepository: ForecastRepository) {
    fun requestLocations(query: String): Single<List<Location>> {
        return forecastRepository.getPossibleLocations(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}