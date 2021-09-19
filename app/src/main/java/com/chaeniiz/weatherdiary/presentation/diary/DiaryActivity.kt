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
import kotlinx.android.synthetic.main.activity_diary.*

class DiaryActivity : AppCompatActivity(), DiaryView {

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

    private val presenter: DiaryPresenter by lazy {
        DiaryPresenter(this, this)
    }

    private val launcherForCitiesDialogActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.let {
                presenter.onActivityResult(
                    it.getStringExtra(CitiesDialogActivity.RESULT_LOCATION)!!,
                    it.getStringExtra(CitiesDialogActivity.RESULT_WEATHER)!!
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        locationTextView.setOnClickListener {
            presenter.onLocationEditTextClicked()
        }
        editButton.setOnClickListener {
            presenter.onEditButtonClicked(
                id = intent.getIntExtra(KEY_DIARY_ID, 0),
                content = contentEditText.text.toString()
            )
        }
        deleteButton.setOnClickListener {
            presenter.onDeleteButtonClicked()
        }

        presenter.onCreate(
            id = intent.getIntExtra(KEY_DIARY_ID, 0)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setLocationTextView(location: String, weather: String) {
        val text = location.includeCommaAndSpace() + weather
        locationTextView.text = text
    }

    override fun setUpdatedAtTextView(updatedAt: String) {
        updatedAtTextView.text = updatedAt
    }

    override fun setContentTextView(content: String) {
        contentEditText.setText(content)
    }

    override fun showErrorDialog(emptyContent: Boolean) {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(
                if (emptyContent)
                    R.string.error_no_content
                else
                    R.string.error_no_location
            )
            setPositiveButton(R.string.general_accept) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun showDeleteConfirmDialog() {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(R.string.delete_confirm_dialog_message)
            setPositiveButton(R.string.general_delete) { _, _ ->
                presenter.onDeleteConfirmed(id = intent.getIntExtra(KEY_DIARY_ID, 0))
            }
            setNegativeButton(R.string.general_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    override fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(launcherForCitiesDialogActivity, this, ViewMode.EDIT)
    }
}
