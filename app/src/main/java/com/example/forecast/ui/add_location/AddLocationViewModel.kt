package com.example.forecast.ui.add_location

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.domain.AddLocationInteractor
import com.example.forecast.domain.GetPossibleLocationsInteractor
import com.example.forecast.ui.select_location.SelectLocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

const val MAX_SUGGESTIONS_COUNT = 10
const val DEFAULT_CURRENT_LOCATION_NAME = "Current location"

class AddLocationViewModel(val getPossibleLocationsInteractor: GetPossibleLocationsInteractor,
                           val addLocationInteractor: AddLocationInteractor) : ViewModel() {
    val TAG = AddLocationViewModel::class.simpleName
    val locationSubject = BehaviorSubject.create<Location?>()
    val possibleLocationsSubject = BehaviorSubject.create<List<Location>>()
    val addedLocationSubject = PublishSubject.create<Location>()

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { androidLocation ->
            //TODO: Get location name via geocoding
            androidLocation?.let {
                val location = Location(DEFAULT_CURRENT_LOCATION_NAME, androidLocation.latitude, androidLocation.longitude)
                locationSubject.onNext(location)
            }
        }
    }

    fun getPossibleLocations(query: String){
        getPossibleLocationsInteractor.requestLocations(query).subscribe ({ locations ->
            possibleLocationsSubject.onNext(locations.take(MAX_SUGGESTIONS_COUNT))
        },
            {
                    error -> Log.e(TAG, error.message?:error.stackTraceToString())
            }
        )
    }

    fun addLocationToSelected(location: Location) {
        addLocationInteractor.addLocation(location).subscribe({ _->
            addedLocationSubject.onNext(location)
        },
            {
                    error -> Log.e(TAG, error.message?:error.stackTraceToString())
            }
        )
    }
}