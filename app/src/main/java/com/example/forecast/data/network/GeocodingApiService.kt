package com.example.forecast.data.network

import com.example.forecast.data.db.Location
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("direct")
    fun getCities(@Query("q") query: String,
                  @Query("appid") appId: String): Observable<List<Location>>
}