package com.example.forecast.ui.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.data.network.BaseWeatherInfo
import com.example.forecast.domain.GetForecastInteractor
import com.example.forecast.ui.add_location.AddLocationViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ForecastViewModel(val getForecastInteractor: GetForecastInteractor) : ViewModel() {
    val forecastSubject = BehaviorSubject.create<BaseWeatherInfo>()
    val TAG = ForecastViewModel::class.simpleName

    fun getForecast(location: Location) {
        getForecastInteractor.getForecast(location).subscribe({ weather->
            forecastSubject.onNext(weather)
        },
        {
                error -> Log.e(TAG, error.message?:error.stackTraceToString())
        }
        )
    }
}