package com.example.forecast.ui.select_location


import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.domain.RemoveLocationInteractor
import com.example.forecast.domain.RequestSavedLocationsInteractor
import io.reactivex.rxjava3.subjects.BehaviorSubject

class SelectLocationViewModel(val requestSavedLocationsInteractor: RequestSavedLocationsInteractor,
                              val removeLocationInteractor: RemoveLocationInteractor): ViewModel() {
    val locationsSubject = BehaviorSubject.create<List<Location>>()
    init {

    }

    fun requestLocations() {
        requestSavedLocationsInteractor.requestLocations().subscribe({
            locations -> locationsSubject.onNext(locations)
        })
    }

    fun removeLocation(locationId: Long) {
        removeLocationInteractor.removeLocation(locationId).subscribe({
            _ -> requestLocations()
        })
    }
}