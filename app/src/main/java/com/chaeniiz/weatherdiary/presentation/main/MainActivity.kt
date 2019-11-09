package com.chaeniiz.weatherdiary.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.data.local.database.DiaryDatabase
import com.chaeniiz.weatherdiary.presentation.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this, this)
    }
    private lateinit var database: DiaryDatabase
    private var location: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = DiaryDatabase.getInstance(this)!!
        val r = Runnable {
            location = database.diaryDao().getDiary(0)?.location ?: ""
        }
        Thread(r).start()

        writeButton.onClick {
            presenter.onWriteButtonClicked()
        }

        presenter.onCreate()
    }

    override fun setTextView(text: String) {
        textView.text = location
    }

    override fun startWriteActivity() {
        WriteActivity.start(this)
    }
}
