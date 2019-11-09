package com.chaeniiz.weatherdiary.presentation.main

import android.content.Context
import com.chaeniiz.entity.entities.CurrentWeather
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.data.network.repositories.CurrentWeatherRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import usecases.GetCurrentWeather
import usecases.GetDiary

class MainPresenter(
    val view: MainView,
    context: Context,
    private val getCurrentWeather: GetCurrentWeather = GetCurrentWeather(
        CurrentWeatherRepository(context)
    ),
    private val getDiary: GetDiary = GetDiary(
        DiaryRepository(context)
    )
) {
    fun onCreate() {
        getCurrentWeather("Seoul")
        getDiary(0)
    }

    private fun getCurrentWeather(city: String) {
        getCurrentWeather.apply {
            this.city = city
        }.execute(object : DefaultSingleObserver<CurrentWeather>() {
            override fun onSuccess(t: CurrentWeather) {
                view.setWeatherTextView(t.weather[0].main)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                e.message?.let { view.setWeatherTextView(it) }
                view.setWeatherTextView(e.toString())
            }
        })
    }

    private fun getDiary(id: Int) {
        getDiary.apply {
            this.id = id
        }.execute(object : DefaultSingleObserver<Diary>() {
            override fun onSuccess(t: Diary) {
                view.setContentTextView(t.content)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                e.message?.let { view.setContentTextView(it) }
                view.setContentTextView(e.toString())
            }
        })
    }

    fun onWriteButtonClicked() {
        view.startWriteActivity()
    }
}
