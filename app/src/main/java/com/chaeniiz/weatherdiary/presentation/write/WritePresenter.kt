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

    private var location: String = ""
    private var weather: String = ""

    fun onCreate() {
        /* explicitly empty */
    }

    fun onWriteButtonClicked(content: String) {
        when {
            location == "" || weather == "" -> view.showErrorDialog(emptyContent = false)
            content == "" -> view.showErrorDialog(emptyContent = true)
            else -> writeDiary(location, weather, content, Date())
        }
    }

    fun onLocationEditTextClicked() {
        view.startCitiesDialogActivity()
    }

    fun onActivityResult(location: String, weather: String) {
        this.location = location
        this.weather = weather
        view.setLocationTextView(location, weather)
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
                view.finish()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }
}
