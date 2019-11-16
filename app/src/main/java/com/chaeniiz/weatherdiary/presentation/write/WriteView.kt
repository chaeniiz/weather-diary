package com.chaeniiz.weatherdiary.presentation.write

interface WriteView {
    fun finish()
    fun showCityDialog()
    fun dismissCityDialog()
    fun setLocationTextView(location: String, weather: String)
    fun showErrorDialog(emptyContent: Boolean)
    fun showErrorToast()
}
