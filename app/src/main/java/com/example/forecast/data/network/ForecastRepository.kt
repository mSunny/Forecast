package com.example.forecast.data.network

import com.example.forecast.data.db.Location
import io.reactivex.rxjava3.core.Single

interface ForecastRepository {
    fun getPossibleLocations(query: String): Single<List<Location>>
    fun getForecastByCityId(cityId: String): Single<BaseWeatherInfo>
    fun getForecastByLatLng(latitude: Double, longitude: Double): Single<BaseWeatherInfo>
}