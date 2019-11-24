package com.chaeniiz.weatherdiary.presentation.write

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
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

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
            presenter.onWriteButtonClicked(content = contentEditText.text.toString())
        }

        locationTextView.onClick {
            presenter.onLocationEditTextClicked()
        }

        presenter.onCreate()
    }

    override fun setLocationTextView(location: String, weather: String) {
        val text = location.includeCommaAndSpace() + weather
        locationTextView.text = text
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

    override fun showErrorToast() {
        toast(R.string.general_error)
    }

    override fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(this, ViewMode.WRITE)
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
