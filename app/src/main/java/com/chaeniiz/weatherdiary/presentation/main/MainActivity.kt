package com.chaeniiz.weatherdiary.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        writeButton.onClick {
            presenter.onWriteButtonClicked()
        }

        presenter.onCreate()
    }

    override fun setWeatherTextView(weather: String) {
        weatherTextView.text = weather
    }

    override fun setContentTextView(content: String) {
        contentTextView.text = content
    }

    override fun startWriteActivity() {
        WriteActivity.start(this)
    }
}
