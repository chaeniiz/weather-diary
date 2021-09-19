package com.chaeniiz.weatherdiary.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.diary.DiaryActivity
import com.chaeniiz.weatherdiary.presentation.write.WriteActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeView {

    private val presenter: HomePresenter by lazy {
        HomePresenter(this, this)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, HomeActivity::class.java)
            )
        }
    }

    private val launcherForWriteActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        presenter.onActivityResult()
    }

    private val launcherForDiaryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        presenter.onActivityResult()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        writeButton.setOnClickListener {
            presenter.onWriteButtonClicked()
        }

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun startWriteActivity() {
        WriteActivity.startForResult(launcherForWriteActivity, this)
    }

    override fun setAdapter(diaries: List<Diary>) {
        with(diaryRecyclerView) {
            adapter = HomeRecyclerAdapter(
                diaries.sortedByDescending { it.updatedAt },
                presenter::onDiaryClicked
            )
            layoutManager = LinearLayoutManager(context)
        }
        diaryRecyclerView.visibility = View.VISIBLE
        emptyTextView.visibility = View.GONE
    }

    override fun showDiary(id: Int) {
        DiaryActivity.startForResult(launcherForDiaryActivity, this, id)
    }

    override fun showEmptyView() {
        diaryRecyclerView.visibility = View.GONE
        emptyTextView.visibility = View.VISIBLE
    }

    override fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }
}
