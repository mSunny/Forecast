package com.example.forecast.ui.forecast

import androidx.lifecycle.ViewModel
import com.example.forecast.data.db.Location
import com.example.forecast.data.network.BaseWeatherInfo
import com.example.forecast.domain.GetForecastInteractor
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ForecastViewModel(val getForecastInteractor: GetForecastInteractor) : ViewModel() {
    val forecastSubject = BehaviorSubject.create<BaseWeatherInfo>()

    fun getForecast(location: Location) {
        getForecastInteractor.getForecast(location).subscribe{ weather->
            forecastSubject.onNext(weather)
        }
    }
}