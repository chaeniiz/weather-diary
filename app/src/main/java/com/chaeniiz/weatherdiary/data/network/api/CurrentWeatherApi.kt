package com.chaeniiz.weatherdiary.data.network.api

import com.chaeniiz.weatherdiary.data.network.response.GetCurrentWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApi {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") appId: String
    ): Single<GetCurrentWeatherResponse>
}
