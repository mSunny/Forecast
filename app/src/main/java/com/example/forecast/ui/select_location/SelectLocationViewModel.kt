package com.example.forecast.ui.select_location


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.domain.RemoveLocationInteractor
import com.example.forecast.domain.RequestSavedLocationsInteractor
import io.reactivex.rxjava3.subjects.BehaviorSubject

class SelectLocationViewModel(val requestSavedLocationsInteractor: RequestSavedLocationsInteractor,
                              val removeLocationInteractor: RemoveLocationInteractor): ViewModel() {
    val TAG = SelectLocationViewModel::class.simpleName
    val locationsSubject = BehaviorSubject.create<List<Location>>()
    init {
        requestLocations()
    }

    fun requestLocations() {
        requestSavedLocationsInteractor.requestLocations().subscribe({ locations ->
            locationsSubject.onNext(locations)
        },
        {
            error -> Log.e(TAG, error.message?:error.stackTraceToString())
        }
        )
    }

    fun removeLocation(locationId: Long) {
        removeLocationInteractor.removeLocation(locationId).subscribe ({
            requestLocations()
        },
        {
                error -> Log.e(TAG, error.message?:error.stackTraceToString())
        })
    }
}