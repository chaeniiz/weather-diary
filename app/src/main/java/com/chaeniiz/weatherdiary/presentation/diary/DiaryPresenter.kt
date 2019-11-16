package com.chaeniiz.weatherdiary.presentation.diary

import android.content.Context
import com.chaeniiz.entity.entities.City
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import com.chaeniiz.weatherdiary.presentation.convertWeather
import usecases.GetCurrentWeather
import usecases.GetDiary
import usecases.UpdateDiary
import java.util.*

class DiaryPresenter(
    val view: DiaryView,
    context: Context,
    private val getDiary: GetDiary = GetDiary(DiaryRepository(context)),
    private val getCurrentWeather: GetCurrentWeather = GetCurrentWeather(
        CurrentWeatherRepository(
            context
        )
    ),
    private val updateDiary: UpdateDiary = UpdateDiary(DiaryRepository(context))
) {

    private var location: String = ""
    private var weather: String = ""

    fun onDestroy() {
        getDiary.dispose()
    }

    fun onCreate(id: Int) {
        getDiary(id)
    }

    fun onLocationEditTextClicked() {
        view.showCityDialog()
    }

    fun onCityClicked(city: City) {
        location = city.formattedString
        getCurrentWeather(city)
    }

    fun onEditButtonClicked(id: Int, content: String) {
        updateDiary(id, content)
    }

    private fun getDiary(id: Int) {
        getDiary.apply {
            this.id = id
        }.execute(object : DefaultSingleObserver<Diary>() {
            override fun onSuccess(t: Diary) {
                location = t.location
                weather = t.weather
                view.setLocationTextView(
                    location = location,
                    weather = weather
                )
                view.setContentTextView(t.content)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    private fun getCurrentWeather(city: City) {
        getCurrentWeather.apply {
            this.city = city.value
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                weather = t.weather.first().convertWeather()
                view.setLocationTextView(
                    location = city.formattedString,
                    weather = t.weather.first().convertWeather()
                )
                view.dismissCityDialog()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    private fun updateDiary(id: Int, content: String) {
        updateDiary.apply {
            diary = Diary(
                id = id,
                location = location,
                weather = weather,
                content = content,
                updatedAt = Date()
            )
        }.execute(object : DefaultCompletableObserver() {
            override fun onComplete() {
                view.finish()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }
}
