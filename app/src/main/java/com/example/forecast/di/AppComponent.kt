package com.example.forecast.di

import com.example.forecast.ui.add_location.AddLocationFragment
import com.example.forecast.ui.forecast.ForecastFragment
import com.example.forecast.ui.main.MainActivity
import com.example.forecast.ui.select_location.SelectLocationFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RoomModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(fragment: SelectLocationFragment)
    fun inject(fragment: AddLocationFragment)
    fun inject(fragment: ForecastFragment)
    fun inject(activity: MainActivity)
}