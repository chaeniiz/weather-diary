package com.chaeniiz.weatherdiary.data.network.repositories

import android.content.Context
import com.chaeniiz.domain.repositories.CurrentWeatherRepository
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.weatherdiary.data.network.response.toEntity
import com.chaeniiz.weatherdiary.data.retrofit
import io.reactivex.Single

class CurrentWeatherRepository(private val context: Context) : CurrentWeatherRepository {
    override fun getCurrentWeather(city: String): Single<CurrentWeather> =
        context.retrofit.currentWeatherApi.getCurrentWeather(city, "e4df7c76d3271f1aa12284310650eb86").map {
            it.toEntity()
        }
}
