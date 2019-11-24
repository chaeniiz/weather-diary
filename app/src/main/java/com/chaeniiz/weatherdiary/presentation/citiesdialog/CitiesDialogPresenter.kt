package com.chaeniiz.weatherdiary.presentation.citiesdialog

import android.content.Context
import com.chaeniiz.entity.entities.City
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import com.chaeniiz.weatherdiary.presentation.convertWeather
import usecases.GetCurrentWeather

class CitiesDialogPresenter(
    val view: CitiesDialogView,
    context: Context,
    private val getCurrentWeather: GetCurrentWeather = GetCurrentWeather(
        CurrentWeatherRepository(
            context
        )
    )
) {
    private var location: String = ""
    private var weather: String = ""

    fun onCreate(viewMode: ViewMode) {
        when (viewMode) {
            ViewMode.WRITE -> view.setDescriptionTextView(current = true)
            ViewMode.EDIT -> view.setDescriptionTextView(current = false)
        }
    }

    fun onCityClicked(city: City) {
        location = city.formattedString
        getCurrentWeather(city)
    }

    private fun getCurrentWeather(city: City) {
        view.showProgressBar()
        getCurrentWeather.apply {
            this.city = city.value
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                weather = t.weather.first().convertWeather()
                view.setResult(
                    location = city.formattedString,
                    weather = t.weather.first().convertWeather()
                )
                view.finish()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
                view.finish()
            }
        })
    }
}
