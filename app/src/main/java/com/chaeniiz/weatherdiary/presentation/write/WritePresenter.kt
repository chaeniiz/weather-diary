package com.chaeniiz.weatherdiary.presentation.write

import android.content.Context
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import usecases.InsertDiary
import java.util.*

class WritePresenter(
    val view: WriteView,
    context: Context,
    private val insertDiary: InsertDiary = InsertDiary(DiaryRepository(context))
) {
    fun onCreate() {
        /* explicitly empty */
    }

    fun onWriteButtonClicked(location: String, weather: String, content: String) {
        writeDiary(0, location, weather, content, Date())
    }

    private fun writeDiary(
        id: Int,
        location: String,
        weather: String,
        content: String,
        updatedAt: Date
    ) {
        insertDiary.apply {
            diary = Diary(id, location, weather, content, updatedAt)
        }.execute(object : DefaultCompletableObserver() {
            override fun onComplete() {
                view.finish()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        })
    }
}
