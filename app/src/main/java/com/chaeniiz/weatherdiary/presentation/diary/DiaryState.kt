package com.chaeniiz.weatherdiary.presentation.diary

sealed class DiaryState {
    data class GetDiarySucceed(
        val location: String,
        val weather: String,
        val updatedAt: String,
        val content: String
    ) : DiaryState()

    object UpdateDiarySucceed : DiaryState()

    object DeleteDiarySucceed : DiaryState()

    sealed class Error {
        object EmptyContent : DiaryState()

        object EmptyLocation : DiaryState()

        object Server : DiaryState()
    }
}
