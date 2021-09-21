package com.chaeniiz.weatherdiary.presentation.citiesdialog

sealed class CitiesDialogState {
    object Loading : CitiesDialogState()

    data class GetCurrentWeatherSucceed(
        val location: String,
        val weather: String
    ) : CitiesDialogState()

    object GetCurrentWeatherFailed : CitiesDialogState()
}
