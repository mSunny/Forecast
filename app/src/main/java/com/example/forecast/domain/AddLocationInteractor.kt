package com.example.forecast.domain

import com.example.forecast.data.db.Location
import com.example.forecast.data.db.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddLocationInteractor @Inject constructor(private val locationRepository: LocationRepository) {
    fun addLocation(location: Location): Single<Long> =
        locationRepository.addLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}