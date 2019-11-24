package com.chaeniiz.weatherdiary.presentation.home

import com.chaeniiz.entity.entities.Diary

interface HomeView {
    fun startWriteActivity()
    fun setAdapter(diaries: List<Diary>)
    fun showDiary(id: Int)
    fun showEmptyView()
    fun showErrorToast()
}
