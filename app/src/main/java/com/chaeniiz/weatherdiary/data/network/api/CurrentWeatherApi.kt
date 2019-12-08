package com.chaeniiz.weatherdiary.data.network.api

import com.chaeniiz.weatherdiary.data.ApiClient
import com.chaeniiz.weatherdiary.data.network.response.GetCurrentWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrentWeatherApi {

    @Headers(
        "${ApiClient.CONNECT_TIMEOUT}:10",
        "${ApiClient.READ_TIMEOUT}:30",
        "${ApiClient.WRITE_TIMEOUT}:10"
    )

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") appId: String
    ): Single<GetCurrentWeatherResponse>
}
