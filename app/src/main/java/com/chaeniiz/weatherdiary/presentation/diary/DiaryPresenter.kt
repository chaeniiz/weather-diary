package com.chaeniiz.weatherdiary.presentation.diary

import android.content.Context
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import usecases.GetDiaries

class DiaryPresenter(
    val view: DiaryView,
    context: Context,
    private val getDiaries: GetDiaries = GetDiaries(DiaryRepository(context))
) {
    fun onCreate() {
        getDiaries()
    }

    private fun getDiaries() {
        getDiaries.execute(object : DefaultSingleObserver<List<Diary>>() {
            override fun onSuccess(t: List<Diary>) {
                view.setAdapter(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        })
    }

    fun onWriteButtonClicked() {
        view.startWriteActivity()
    }

    fun onDiaryClicked(id: Int) {
        view.showToast("id: $id")
    }
}
