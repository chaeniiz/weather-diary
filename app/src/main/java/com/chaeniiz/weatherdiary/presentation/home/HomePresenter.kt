package com.chaeniiz.weatherdiary.presentation.home

import android.content.Context
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import usecases.GetDiaries

class HomePresenter(
    val view: HomeView,
    context: Context,
    private val getDiaries: GetDiaries = GetDiaries(DiaryRepository(context))
) {
    fun onDestroy() {
        getDiaries.dispose()
    }

    fun onCreate() {
        getDiaries()
    }

    private fun getDiaries() {
        getDiaries.execute(object : DefaultSingleObserver<List<Diary>>() {
            override fun onSuccess(t: List<Diary>) {
                if (t.isNotEmpty())
                    view.setAdapter(t)
                else
                    view.showEmptyView()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    fun onWriteButtonClicked() {
        view.startWriteActivity()
    }

    fun onDiaryClicked(id: Int) {
        view.showDiary(id)
    }

    fun onActivityResult() {
        getDiaries()
    }
}
