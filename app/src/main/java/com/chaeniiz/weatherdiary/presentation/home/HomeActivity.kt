package com.chaeniiz.weatherdiary.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import com.chaeniiz.weatherdiary.presentation.diary.DiaryActivity
import com.chaeniiz.weatherdiary.presentation.write.WriteActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.onClick

class HomeActivity : AppCompatActivity(), HomeView {

    private val presenter: HomePresenter by lazy {
        HomePresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        writeButton.onClick {
            presenter.onWriteButtonClicked()
        }

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.WRITE_ACTIVITY_CODE.ordinal -> presenter.onActivityResultFromWrite()
        }
    }

    override fun startWriteActivity() {
        WriteActivity.startForResult(this)
    }

    override fun setAdapter(diaries: List<Diary>) {
        with(diaryRecyclerView) {
            adapter = HomeRecyclerAdapter(
                diaries,
                presenter::onDiaryClicked
            )
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showDiary(id: Int) {
        DiaryActivity.start(this, id)
    }
}
