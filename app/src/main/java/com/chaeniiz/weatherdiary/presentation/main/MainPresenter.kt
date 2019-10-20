package com.chaeniiz.weatherdiary.presentation.main

import android.content.Context
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import usecases.GetCurrentWeather

class MainPresenter(
    val view: MainView,
    context: Context,
    private val getCurrentWeather: GetCurrentWeather =
        GetCurrentWeather(
            CurrentWeatherRepository(
                context
            )
        )
) {
    fun onCreate() {
        getCurrentWeather("Seoul")
    }

    private fun getCurrentWeather(city: String) {
        getCurrentWeather.apply {
            this.city = city
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                view.setTextView(t.name)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                e.message?.let { view.setTextView(it) }
                view.setTextView(e.toString())
            }
        })
    }
}
