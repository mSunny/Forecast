package com.example.forecast.ui.main

import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.domain.RequestSavedLocationsInteractor
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainViewModel(val requestSavedLocationsInteractor: RequestSavedLocationsInteractor): ViewModel() {
    enum class CurrentFragmentShown{FORECAST, ADD_LOCATION, SELECT_LOCATION}
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
        requestSavedLocationsInteractor.requestLocations().subscribe { locations ->
            if (locations.isEmpty()) {
                switchFragment(CurrentFragmentShown.ADD_LOCATION)
            } else if (locations.size == 1) {
                selectedLocation = locations[0]
                switchFragment(CurrentFragmentShown.FORECAST)
            } else {
                switchFragment(CurrentFragmentShown.SELECT_LOCATION)
            }
        }
    }

    fun locationSelected(location: Location) {
        selectedLocation = location
        switchFragment(CurrentFragmentShown.FORECAST)
    }
}
