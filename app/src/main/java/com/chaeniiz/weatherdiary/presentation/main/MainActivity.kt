package com.chaeniiz.weatherdiary.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate()
    }

    override fun setTextView(text: String) {
        textView.text = text
    }
}
