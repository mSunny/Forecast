package com.example.forecast

import android.app.Application
import android.content.Context
import com.example.forecast.di.DiManager

class App: Application() {
    lateinit var diManager: DiManager

    override fun onCreate() {
        super.onCreate()
        diManager = DiManager(this)
    }

    val Context.diManager: DiManager
        get() = when (this) {
            is App -> diManager
            else -> applicationContext.diManager
        }
}