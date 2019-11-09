package com.chaeniiz.weatherdiary.presentation.diary

import com.chaeniiz.entity.entities.Diary

interface DiaryView {
    fun startWriteActivity()
    fun setAdapter(diaries: List<Diary>)
    fun showToast(text: String)
}
