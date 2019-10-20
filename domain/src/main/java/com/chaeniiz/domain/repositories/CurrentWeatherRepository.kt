package com.chaeniiz.domain.repositories

import com.chaeniiz.entity.entities.CurrentWeather
import io.reactivex.Single

interface CurrentWeatherRepository {
    fun getCurrentWeather(
        city: String
    ): Single<CurrentWeather>
}
