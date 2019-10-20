package com.chaeniiz.weatherdiary

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(
    context: Context
) : ApiClientSpec {

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val adapter: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/weather?q=")
            .client(buildOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun buildOkHttpClient(
        timeout: Long = 20
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
    }

    override val currentWeatherApi: CurrentWeatherApi by lazy {
        adapter.create(CurrentWeatherApi::class.java)
    }
}
