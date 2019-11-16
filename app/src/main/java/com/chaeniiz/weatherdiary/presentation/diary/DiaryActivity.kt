package com.chaeniiz.weatherdiary.presentation.diary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R

class DiaryActivity : AppCompatActivity(), DiaryView {

    private val presenter: DiaryPresenter by lazy {
        DiaryPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
