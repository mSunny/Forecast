package com.example.forecast.data.network

import com.example.forecast.data.db.Location
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(val openWeatherApiService: OpenWeatherApiService,
                                                 val geocodingApiService: GeocodingApiService): ForecastRepository {
    val apiKey = "5f01e2253e1a6bf3f2172c1c0482658d"
    override fun getPossibleLocations(query: String): Single<List<Location>> {
        return geocodingApiService.getCities(query, apiKey).onErrorReturn { emptyList<Location>() }.singleOrError()
    }

    override fun getForecastByCityId(cityId: String): Single<BaseWeatherInfo> {
        return openWeatherApiService.getWeatherByCityId(cityId, apiKey).map{
            it.info
        }.singleOrError()
    }

    override fun getForecastByLatLng(latitude: Double, longitude: Double): Single<BaseWeatherInfo> {
        return openWeatherApiService.getWeatherByLatLng(latitude, longitude, apiKey).map{
            it.info
        }.singleOrError()
    }

}