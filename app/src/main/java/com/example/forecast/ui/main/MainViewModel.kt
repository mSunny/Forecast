package com.example.forecast.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.domain.RequestSavedLocationsInteractor
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainViewModel(val requestSavedLocationsInteractor: RequestSavedLocationsInteractor): ViewModel() {
    enum class CurrentFragmentShown{FORECAST, ADD_LOCATION, SELECT_LOCATION}
    val TAG = MainViewModel::class.simpleName
    var selectedLocation: Location? = null
    val currentFragmentSubject = BehaviorSubject.create<CurrentFragmentShown>()

    init {
        getSavedLocations()
    }
    fun switchFragment(currentFragmentShown: CurrentFragmentShown) {
        currentFragmentSubject.onNext(currentFragmentShown)
    }

    fun locationAdded(location: Location) {
        selectedLocation = location
        switchFragment(CurrentFragmentShown.FORECAST)
    }

    fun getSavedLocations() {
        requestSavedLocationsInteractor.requestLocations().subscribe ({ locations ->
            when {
                locations.isEmpty() -> {
                    switchFragment(CurrentFragmentShown.ADD_LOCATION)
                }
                locations.size == 1 -> {
                    selectedLocation = locations[0]
                    switchFragment(CurrentFragmentShown.FORECAST)
                }
                else -> {
                    switchFragment(CurrentFragmentShown.SELECT_LOCATION)
                }
            }
        } ,
            {
                    error -> Log.e(TAG, error.message?:error.stackTraceToString())
            }
        )
    }

    fun locationSelected(location: Location) {
        selectedLocation = location
        switchFragment(CurrentFragmentShown.FORECAST)
    }
}
