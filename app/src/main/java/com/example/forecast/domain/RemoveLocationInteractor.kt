package com.example.forecast.domain

import com.example.forecast.data.db.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RemoveLocationInteractor @Inject constructor(private val locationRepository: LocationRepository) {
    fun removeLocation(locationId: Long): Single<Int> =
        locationRepository.deleteLocationById(locationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}