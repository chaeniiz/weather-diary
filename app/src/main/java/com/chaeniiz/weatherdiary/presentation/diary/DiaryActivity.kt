package com.chaeniiz.weatherdiary.presentation.diary

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.entity.entities.City
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import kotlinx.android.synthetic.main.activity_diary.*
import org.jetbrains.anko.*

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
    lateinit var cityDialog: AlertDialogBuilder

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

    override fun showCityDialog() {
        cityDialog = AlertDialogBuilder(this).apply {
            val dialogView =
                LayoutInflater.from(this@DiaryActivity).inflate(R.layout.dialog_cities, null)
            dialogView.find<TextView>(R.id.descriptionTextView).text =
                getString(R.string.city_dialog_description_past)
            dialogView.find<TextView>(R.id.seoulTextView)
                .onClick { presenter.onCityClicked(City.SEOUL) }
            dialogView.find<TextView>(R.id.incheonTextView)
                .onClick { presenter.onCityClicked(City.INCHEON) }
            dialogView.find<TextView>(R.id.daejeonTextView)
                .onClick { presenter.onCityClicked(City.DAEJEON) }
            dialogView.find<TextView>(R.id.gwangjuTextView)
                .onClick { presenter.onCityClicked(City.GWANGJU) }
            dialogView.find<TextView>(R.id.busanTextView)
                .onClick { presenter.onCityClicked(City.BUSAN) }
            dialogView.find<TextView>(R.id.daeguTextView)
                .onClick { presenter.onCityClicked(City.DAEGU) }
            dialogView.find<TextView>(R.id.ulsanTextView)
                .onClick { presenter.onCityClicked(City.ULSAN) }
            dialogView.find<TextView>(R.id.jejuTextView)
                .onClick { presenter.onCityClicked(City.JEJU) }
            customView(dialogView)
        }
        cityDialog.show()
    }

    override fun showErrorDialog(emptyContent: Boolean) {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(
                if (emptyContent)
                    R.string.error_no_content
                else
                    R.string.error_no_location
            )
            setPositiveButton(R.string.general_dialog_accept) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun showDeleteConfirmDialog() {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(R.string.delete_confirm_dialog_message)
            setPositiveButton(R.string.general_dialog_delete) { _, _ ->
                presenter.onDeleteConfirmed(id = intent.getIntExtra(KEY_DIARY_ID, 0))
            }
            setNegativeButton(R.string.general_dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun dismissCityDialog() {
        if (::cityDialog.isInitialized)
            cityDialog.dismiss()
    }

    override fun showErrorToast() {
        toast(R.string.general_error)
    }
}
