package com.chaeniiz.weatherdiary.data

import android.content.Context
import com.chaeniiz.weatherdiary.data.network.api.CurrentWeatherApi
import com.chaeniiz.weatherdiary.WeatherDiary

interface ApiClientSpec {
    val currentWeatherApi: CurrentWeatherApi
}

val Context.retrofit: ApiClientSpec
    get() = (applicationContext as WeatherDiary).api
