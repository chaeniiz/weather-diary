package com.chaeniiz.weatherdiary.presentation.main

interface MainView {
    fun setWeatherTextView(weather: String)
    fun setContentTextView(content: String)
    fun startWriteActivity()
}
