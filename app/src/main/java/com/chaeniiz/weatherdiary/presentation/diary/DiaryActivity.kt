package com.chaeniiz.weatherdiary.presentation.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import com.chaeniiz.weatherdiary.presentation.citiesdialog.CitiesDialogActivity
import com.chaeniiz.weatherdiary.presentation.citiesdialog.ViewMode
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import kotlinx.android.synthetic.main.activity_diary.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class DiaryActivity : AppCompatActivity(), DiaryView {

    companion object {
        const val KEY_DIARY_ID = "diary_id"

        fun startForResult(activity: Activity, id: Int) {
            with(activity) {
                startActivityForResult(
                    intentFor<DiaryActivity>(
                        KEY_DIARY_ID to id
                    ),
                    RequestCode.DIARY_ACTIVITY_CODE.ordinal
                )
            }
        }
    }

    private val presenter: DiaryPresenter by lazy {
        DiaryPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        locationTextView.onClick {
            presenter.onLocationEditTextClicked()
        }
        editButton.onClick {
            presenter.onEditButtonClicked(
                id = intent.getIntExtra(KEY_DIARY_ID, 0),
                content = contentEditText.text.toString()
            )
        }
        deleteButton.onClick {
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
        toast(R.string.general_error)
    }

    override fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(this, ViewMode.EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.CITIES_DIALOG_ACTIVITY_CODE.ordinal -> {
                if (resultCode == Activity.RESULT_OK)
                    data?.let {
                        presenter.onActivityResult(
                            it.getStringExtra(CitiesDialogActivity.RESULT_LOCATION),
                            it.getStringExtra(CitiesDialogActivity.RESULT_WEATHER)
                        )
                    }
            }
        }
    }
}
