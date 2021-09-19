package com.chaeniiz.weatherdiary.presentation.citiesdialog

import android.content.Context
import androidx.lifecycle.ViewModel
import com.chaeniiz.entity.entities.City
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import com.chaeniiz.weatherdiary.presentation.convertWeather
import io.reactivex.subjects.BehaviorSubject
import usecases.GetCurrentWeather

class CitiesDialogViewModel(
    context: Context,
    private val getCurrentWeather: GetCurrentWeather = GetCurrentWeather(
        CurrentWeatherRepository(
            context
        )
    )
) : ViewModel() {

    val state: BehaviorSubject<CitiesDialogState> = BehaviorSubject.create()

    fun getCurrentWeather(city: City) {
        state.onNext(CitiesDialogState.Loading)
        getCurrentWeather.apply {
            this.city = city.value
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                state.onNext(
                    CitiesDialogState.GetCurrentWeatherSucceed(
                        location = city.formattedString,
                        weather = t.weather.first().convertWeather()
                    )
                )
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(CitiesDialogState.GetCurrentWeatherFailed)
            }
        })
    }
}
