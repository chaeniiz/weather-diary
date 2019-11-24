package com.chaeniiz.weatherdiary.presentation.diary

import android.content.Context
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.DiaryRepository
import com.chaeniiz.weatherdiary.presentation.base.DefaultCompletableObserver
import com.chaeniiz.weatherdiary.presentation.base.DefaultSingleObserver
import com.chaeniiz.weatherdiary.presentation.toFormattedString
import usecases.DeleteDiary
import usecases.GetDiary
import usecases.UpdateDiary
import java.util.*

class DiaryPresenter(
    val view: DiaryView,
    context: Context,
    private val getDiary: GetDiary = GetDiary(DiaryRepository(context)),
    private val updateDiary: UpdateDiary = UpdateDiary(DiaryRepository(context)),
    private val deleteDiary: DeleteDiary = DeleteDiary(DiaryRepository(context))
) {

    private var location: String = ""
    private var weather: String = ""

    fun onDestroy() {
        getDiary.dispose()
    }

    fun onCreate(id: Int) {
        getDiary(id)
    }

    fun onLocationEditTextClicked() {
        view.startCitiesDialogActivity()
    }

    fun onEditButtonClicked(id: Int, content: String) {
        when {
            location == "" || weather == "" -> view.showErrorDialog(emptyContent = false)
            content == "" -> view.showErrorDialog(emptyContent = true)
            else -> updateDiary(id, content)
        }
    }

    fun onDeleteButtonClicked() {
        view.showDeleteConfirmDialog()
    }

    fun onDeleteConfirmed(id: Int) {
        deleteDiary(id)
    }

    fun onActivityResult(location: String, weather: String) {
        this.location = location
        this.weather = weather
        view.setLocationTextView(location, weather)
    }

    private fun getDiary(id: Int) {
        getDiary.apply {
            this.id = id
        }.execute(object : DefaultSingleObserver<Diary>() {
            override fun onSuccess(t: Diary) {
                location = t.location
                weather = t.weather
                view.setLocationTextView(
                    location = location,
                    weather = weather
                )
                view.setUpdatedAtTextView(
                    t.updatedAt.toFormattedString("yyyy년 M월 dd일 a H시 mm분")
                )
                view.setContentTextView(t.content)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    private fun updateDiary(id: Int, content: String) {
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
                view.finish()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view.showErrorToast()
            }
        })
    }

    private fun deleteDiary(id: Int) {
        deleteDiary.apply {
            this.id = id
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
