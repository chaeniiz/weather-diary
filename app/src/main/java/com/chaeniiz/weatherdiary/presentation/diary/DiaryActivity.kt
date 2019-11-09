package com.chaeniiz.weatherdiary.presentation.diary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.write.WriteActivity
import kotlinx.android.synthetic.main.activity_diary.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class DiaryActivity : AppCompatActivity(), DiaryView {

    private val presenter: DiaryPresenter by lazy {
        DiaryPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        writeButton.onClick {
            presenter.onWriteButtonClicked()
        }

        presenter.onCreate()
    }

    override fun startWriteActivity() {
        WriteActivity.start(this)
    }

    override fun setAdapter(diaries: List<Diary>) {
        with(diaryRecyclerView) {
            adapter = DiaryRecyclerAdapter(
                diaries,
                presenter::onDiaryClicked
            )
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showToast(text: String) {
        toast(text)
    }
}
