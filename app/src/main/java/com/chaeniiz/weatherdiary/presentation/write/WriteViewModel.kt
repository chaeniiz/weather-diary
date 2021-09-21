package com.chaeniiz.weatherdiary.presentation.write

import android.content.Context
import androidx.lifecycle.ViewModel
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import io.reactivex.subjects.BehaviorSubject
import usecases.InsertDiary
import java.util.*

class WriteViewModel(
    context: Context,
    private val insertDiary: InsertDiary = InsertDiary(DiaryRepository(context))
) : ViewModel() {

    val state: BehaviorSubject<WriteState> = BehaviorSubject.create()

    fun onWriteButtonClicked(
        location: String,
        weather: String,
        content: String
    ) {
        when {
            location == "" || weather == "" -> state.onNext(WriteState.Error.EmptyLocation)
            content == "" -> state.onNext(WriteState.Error.EmptyContent)
            else -> writeDiary(location, weather, content, Date())
        }
    }

    private fun writeDiary(
        location: String,
        weather: String,
        content: String,
        updatedAt: Date
    ) {
        insertDiary.apply {
            diary = Diary(
                location = location,
                weather = weather,
                content = content,
                updatedAt = updatedAt
            )
        }.execute(object : DefaultCompletableObserver() {
            override fun onComplete() {
                state.onNext(WriteState.WriteDiarySucceed)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                state.onNext(WriteState.Error.Server)
            }
        })
    }
}
