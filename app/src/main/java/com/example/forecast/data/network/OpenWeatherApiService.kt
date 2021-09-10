package com.example.forecast.data.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("weather")
    fun getWeatherByCityId(@Query("id") cityId: String, @Query("appid") appId: String): Observable<ForecastResponse>

    @GET("weather")
    fun getWeatherByLatLng(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") appId: String): Observable<ForecastResponse>
}