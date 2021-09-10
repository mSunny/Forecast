package com.example.forecast.domain

import com.example.forecast.data.db.Location
import com.example.forecast.data.network.BaseWeatherInfo
import com.example.forecast.data.network.ForecastRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetForecastInteractor @Inject constructor(private val forecastRepository: ForecastRepository) {
    fun getForecast(location: Location): Single<BaseWeatherInfo> {
        return forecastRepository.getForecastByLatLng(location.latitude, location.longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}