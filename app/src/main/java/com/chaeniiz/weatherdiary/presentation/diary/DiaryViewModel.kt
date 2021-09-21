package com.chaeniiz.weatherdiary.presentation.diary

import android.content.Context
import androidx.lifecycle.ViewModel
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import com.chaeniiz.weatherdiary.presentation.toFormattedString
import io.reactivex.subjects.BehaviorSubject
import usecases.DeleteDiary
import usecases.GetDiary
import usecases.UpdateDiary
import java.util.*

class DiaryViewModel(
    context: Context,
    private val getDiary: GetDiary = GetDiary(DiaryRepository(context)),
    private val updateDiary: UpdateDiary = UpdateDiary(DiaryRepository(context)),
    private val deleteDiary: DeleteDiary = DeleteDiary(DiaryRepository(context))
) : ViewModel() {

    val state: BehaviorSubject<DiaryState> = BehaviorSubject.create()

    fun onEditButtonClicked(
        id: Int,
        content: String,
        location: String,
        weather: String
    ) {
        when {
            location == "" || weather == "" -> state.onNext(DiaryState.Error.EmptyLocation)
            content == "" -> state.onNext(DiaryState.Error.EmptyContent)
            else -> updateDiary(id, content, location, weather)
        }
    }

    fun getDiary(id: Int) {
        getDiary.apply {
            this.id = id
        }.execute(object : DefaultSingleObserver<Diary>() {
            override fun onSuccess(t: Diary) {
                state.onNext(
                    DiaryState.GetDiarySucceed(
                        location = t.location,
                        weather = t.weather,
                        updatedAt = t.updatedAt.toFormattedString("yyyy년 M월 dd일 a H시 mm분"),
                        content = t.content
                    )
                )
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(DiaryState.Error.Server)
            }
        })
    }

    fun deleteDiary(id: Int) {
        deleteDiary.apply {
            this.id = id
        }.execute(object : DefaultCompletableObserver() {
            override fun onComplete() {
                state.onNext(DiaryState.DeleteDiarySucceed)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(DiaryState.Error.Server)
            }
        })
    }

    private fun updateDiary(
        id: Int,
        content: String,
        location: String,
        weather: String
    ) {
        updateDiary.apply {
            diary = Diary(
                id = id,
                location = location,
                weather = weather,
                content = content,
                updatedAt = Date()
            )
        }.execute(object : DefaultCompletableObserver() {
            override fun onComplete() {
                state.onNext(DiaryState.UpdateDiarySucceed)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(DiaryState.Error.Server)
            }
        })
    }
}
