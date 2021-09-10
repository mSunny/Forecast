package com.example.forecast.data.db

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(val locationDao: LocationDao): LocationRepository{
    override fun getSavedLocations(): Single<List<Location>> = locationDao.getAll()

    override fun getLocationById(id: Long): Single<Location> = locationDao.getById(id)

    override fun addLocation(location: Location): Single<Long> {
        return locationDao.insert(location)
    }

    override fun deleteLocationById(locationId: Long): Single<Int> = locationDao.delete(locationId)
}