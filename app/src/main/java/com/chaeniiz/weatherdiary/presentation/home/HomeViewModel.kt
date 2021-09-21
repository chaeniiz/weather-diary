package com.chaeniiz.weatherdiary.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import io.reactivex.subjects.BehaviorSubject
import usecases.GetDiaries

class HomeViewModel(
    context: Context,
    private val getDiaries: GetDiaries = GetDiaries(DiaryRepository(context))
) : ViewModel() {

    val state: BehaviorSubject<HomeState> = BehaviorSubject.create()

    fun getDiaries() {
        getDiaries.execute(object : DefaultSingleObserver<List<Diary>>() {
            override fun onSuccess(t: List<Diary>) {
                if (t.isNotEmpty())
                    state.onNext(HomeState.GetDiariesSucceedWithItems(t))
                else
                    state.onNext(HomeState.GetDiariesSucceedWithoutItems)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(HomeState.GetDiariesFailed)
            }
        })
    }
}
