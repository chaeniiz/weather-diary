package com.chaeniiz.weatherdiary.presentation.citiesdialog

interface CitiesDialogView {
    fun showProgressBar()
    fun setResult(location: String, weather: String)
    fun showErrorToast()
    fun finish()
    fun setDescriptionTextView(current: Boolean)
}
