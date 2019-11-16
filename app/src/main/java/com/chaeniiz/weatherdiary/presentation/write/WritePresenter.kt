package com.chaeniiz.weatherdiary.presentation.write

import android.content.Context
import com.chaeniiz.entity.entities.City
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import usecases.GetCurrentWeather
import usecases.InsertDiary
import java.util.*

class WritePresenter(
    val view: WriteView,
    context: Context,
    private val insertDiary: InsertDiary = InsertDiary(DiaryRepository(context)),
    private val getCurrentWeather: GetCurrentWeather = GetCurrentWeather(
        CurrentWeatherRepository(
            context
        )
    )
) {

    private var location: String = ""
    private var weather: String = ""

    fun onCreate() {
        /* explicitly empty */
    }

    fun onWriteButtonClicked(content: String) {
        when {
            location == "" || weather == "" -> view.showErrorDialog(emptyContent = false)
            content == "" -> view.showErrorDialog(emptyContent = true)
            else -> writeDiary(location, weather, content, Date())
        }
    }

    fun onLocationEditTextClicked() {
        view.showCityDialog()
    }

    fun onCityClicked(city: City) {
        location = city.formattedString
        getCurrentWeather(city)
    }

    private fun getCurrentWeather(city: City) {
        getCurrentWeather.apply {
            this.city = city.value
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                weather = convertWeather(t.weather.first().id)
                view.setLocationTextView(
                    location = city.formattedString,
                    weather = convertWeather(t.weather.first().id)
                )
                view.dismissCityDialog()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    private fun writeDiary(
        location: String,
        weather: String,
        content: String,
        updatedAt: Date
    ) {
        insertDiary.apply {
            diary = Diary(
                location = location,
                weather = weather,
                content = content,
                updatedAt = updatedAt
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

    private fun convertWeather(weatherId: Int): String =
        when (weatherId) {
            in 0..299 -> "뇌우"
            in 300..399 -> "이슬비"
            in 400..599 -> "비"
            in 600..699 -> "눈"
            in 700..799 -> "안개"
            800 -> "맑음"
            else -> "구름 많음"
        }
}
