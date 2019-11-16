package com.chaeniiz.weatherdiary.presentation.diary

interface DiaryView {
    fun setLocationTextView(location: String, weather: String)
    fun setContentTextView(content: String)
    fun showCityDialog()
    fun dismissCityDialog()
    fun showErrorToast()
    fun finish()
}
