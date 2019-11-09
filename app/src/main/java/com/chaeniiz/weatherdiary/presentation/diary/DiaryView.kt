package com.chaeniiz.weatherdiary.presentation.diary

interface DiaryView {
    fun setWeatherTextView(weather: String)
    fun setContentTextView(content: String)
    fun startWriteActivity()
}
