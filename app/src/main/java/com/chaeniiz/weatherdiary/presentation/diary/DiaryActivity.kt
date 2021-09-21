package com.chaeniiz.weatherdiary.presentation.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.citiesdialog.CitiesDialogActivity
import com.chaeniiz.weatherdiary.presentation.citiesdialog.ViewMode
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_diary.*
import org.koin.android.ext.android.inject

class DiaryActivity : AppCompatActivity() {

    companion object {
        const val KEY_DIARY_ID = "diary_id"

        fun startForResult(
            activityResultLauncher: ActivityResultLauncher<Intent>,
            activity: Activity,
            id: Int
        ) {
            activityResultLauncher.launch(
                Intent(activity, DiaryActivity::class.java).apply {
                    putExtra(KEY_DIARY_ID, id)
                }
            )
        }
    }

    private val viewModel: DiaryViewModel by inject()
    private val compositeDisposable = CompositeDisposable()
    private var location: String = ""
    private var weather: String = ""

    private val launcherForCitiesDialogActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.let {
                location = it.getStringExtra(CitiesDialogActivity.RESULT_LOCATION)!!
                weather = it.getStringExtra(CitiesDialogActivity.RESULT_WEATHER)!!
                setLocationTextView(location, weather)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        initialize()
        observeState()

        viewModel.getDiary(
            id = intent.getIntExtra(KEY_DIARY_ID, 0)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun initialize() {
        locationTextView.setOnClickListener {
            startCitiesDialogActivity()
        }
        editButton.setOnClickListener {
            viewModel.onEditButtonClicked(
                id = intent.getIntExtra(KEY_DIARY_ID, 0),
                content = contentEditText.text.toString(),
                location = location,
                weather = weather
            )
        }
        deleteButton.setOnClickListener {
            showDeleteConfirmDialog()
        }
    }

    private fun observeState() {
        compositeDisposable += viewModel.state.subscribe { state ->
            when (state) {
                DiaryState.DeleteDiarySucceed -> {
                    finish()
                }
                DiaryState.Error.EmptyContent -> {
                    showErrorDialog(state)
                }
                DiaryState.Error.EmptyLocation -> {
                    showErrorDialog(state)
                }
                is DiaryState.GetDiarySucceed -> {
                    location = state.location
                    weather = state.weather
                    setLocationTextView(
                        location = location,
                        weather = weather
                    )
                    setUpdatedAtTextView(state.updatedAt)
                    setContentTextView(state.content)
                }
                DiaryState.Error.Server -> {
                    showErrorToast()
                }
                DiaryState.UpdateDiarySucceed -> {
                    finish()
                }
            }
        }
    }

    private fun setLocationTextView(location: String, weather: String) {
        val text = location.includeCommaAndSpace() + weather
        locationTextView.text = text
    }

    private fun setUpdatedAtTextView(updatedAt: String) {
        updatedAtTextView.text = updatedAt
    }

    private fun setContentTextView(content: String) {
        contentEditText.setText(content)
    }

    private fun showErrorDialog(error: DiaryState) {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(
                if (error is DiaryState.Error.EmptyContent)
                    R.string.error_no_content
                else
                    R.string.error_no_location
            )
            setPositiveButton(R.string.general_accept) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(R.string.delete_confirm_dialog_message)
            setPositiveButton(R.string.general_delete) { _, _ ->
                viewModel.deleteDiary(id = intent.getIntExtra(KEY_DIARY_ID, 0))
            }
            setNegativeButton(R.string.general_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    private fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(launcherForCitiesDialogActivity, this, ViewMode.EDIT)
    }
}
