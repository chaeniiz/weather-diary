package com.chaeniiz.weatherdiary.data.network.repositories

import android.content.Context
import com.chaeniiz.domain.repositories.CurrentWeatherRepository
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.weatherdiary.data.network.response.toEntity
import com.chaeniiz.weatherdiary.data.retrofit
import io.reactivex.Single

class CurrentWeatherRepository(private val context: Context) : CurrentWeatherRepository {
    override fun getCurrentWeather(city: String): Single<CurrentWeather> =
        context.retrofit.currentWeatherApi.getCurrentWeather(city, "c605e31ec5115fa19cb889ce4d912430").map {
            it.toEntity()
        }
}
