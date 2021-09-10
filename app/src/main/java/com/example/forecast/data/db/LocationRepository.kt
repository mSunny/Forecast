package com.example.forecast.data.db

import io.reactivex.rxjava3.core.Single

interface LocationRepository {
    fun getSavedLocations(): Single<List<Location>>
    fun getLocationById(id: Long): Single<Location>
    fun addLocation(location: Location): Single<Long>
    fun deleteLocationById(locationId: Long): Single<Int>
}