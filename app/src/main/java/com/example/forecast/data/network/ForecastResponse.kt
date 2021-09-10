package com.example.forecast.data.network

import com.google.gson.annotations.SerializedName

data class ForecastResponse(@SerializedName("main")val info: BaseWeatherInfo) {
}

data class BaseWeatherInfo(@SerializedName("temp") val temperature: Float,
                           @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int)
