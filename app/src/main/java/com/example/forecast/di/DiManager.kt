package com.example.forecast.di

import android.app.Application

class DiManager(application: Application) {
    lateinit var appComponent: AppComponent
        private set

//    var itemComponent: ItemComponent? = null
//        private set

    init {
        createAppComponent(application)
    }

    private fun createAppComponent(application: Application) {
        appComponent = DaggerAppComponent.builder()
            .roomModule(RoomModule(application))
            .build()
    }

}