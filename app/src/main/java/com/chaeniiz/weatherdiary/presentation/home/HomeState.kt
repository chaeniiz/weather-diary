package com.chaeniiz.weatherdiary.presentation.home

import com.chaeniiz.entity.entities.Diary

sealed class HomeState {
    data class GetDiariesSucceedWithItems(
        val diaries: List<Diary>
    ) : HomeState()

    object GetDiariesSucceedWithoutItems : HomeState()

    object GetDiariesFailed : HomeState()
}
