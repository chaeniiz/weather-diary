package com.chaeniiz.weatherdiary.presentation.write

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick

class WriteActivity : AppCompatActivity(), WriteView {

    companion object {
        fun startForResult(activity: Activity) {
            with(activity) {
                startActivityForResult(
                    intentFor<WriteActivity>(),
                    RequestCode.WRITE_ACTIVITY_CODE.ordinal
                )
            }
        }
    }

    private val presenter: WritePresenter by lazy {
        WritePresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        writeButton.onClick {
            presenter.onWriteButtonClicked(
                location = locationEditText.text.toString(),
                weather = weatherEditText.text.toString(),
                content = contentEditText.text.toString()
            )
        }

        presenter.onCreate()
    }
}
