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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

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
        viewModel.getDiaries()
    }

    private val launcherForDiaryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.getDiaries()
    }

    private val viewModel: HomeViewModel by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        writeButton.setOnClickListener {
            startWriteActivity()
        }

        observeState()
        viewModel.getDiaries()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun observeState() {
        compositeDisposable += viewModel.state.subscribe { state ->
            when (state) {
                HomeState.GetDiariesFailed -> {
                    showErrorToast()
                }
                is HomeState.GetDiariesSucceedWithItems -> {
                    setAdapter(state.diaries)
                }
                HomeState.GetDiariesSucceedWithoutItems -> {
                    showEmptyView()
                }
            }
        }
    }

    private fun startWriteActivity() {
        WriteActivity.startForResult(launcherForWriteActivity, this)
    }

    private fun setAdapter(diaries: List<Diary>) {
        with(diaryRecyclerView) {
            adapter = HomeRecyclerAdapter(
                diaries.sortedByDescending { it.updatedAt }
            ) { showDiary(it) }
            layoutManager = LinearLayoutManager(context)
        }
        diaryRecyclerView.visibility = View.VISIBLE
        emptyTextView.visibility = View.GONE
    }

    private fun showDiary(id: Int) {
        DiaryActivity.startForResult(launcherForDiaryActivity, this, id)
    }

    private fun showEmptyView() {
        diaryRecyclerView.visibility = View.GONE
        emptyTextView.visibility = View.VISIBLE
    }

    private fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }
}
