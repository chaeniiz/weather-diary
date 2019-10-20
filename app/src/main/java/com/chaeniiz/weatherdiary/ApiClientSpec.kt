package com.chaeniiz.weatherdiary

import android.content.Context

interface ApiClientSpec {
    val currentWeatherApi: CurrentWeatherApi
}

val Context.retrofit: ApiClientSpec
    get() = (applicationContext as WeatherDiary).api
