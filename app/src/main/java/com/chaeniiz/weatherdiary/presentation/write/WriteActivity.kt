package com.chaeniiz.weatherdiary.presentation.write

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
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity(), WriteView {

    companion object {
        fun startForResult(
            activityResultLauncher: ActivityResultLauncher<Intent>,
            activity: Activity
        ) {
            activityResultLauncher.launch(
                Intent(activity, WriteActivity::class.java)
            )
        }
    }

    private val presenter: WritePresenter by lazy {
        WritePresenter(this, this)
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
        setContentView(R.layout.activity_write)

        writeButton.setOnClickListener {
            presenter.onWriteButtonClicked(content = contentEditText.text.toString())
        }

        locationTextView.setOnClickListener {
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
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    override fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(launcherForCitiesDialogActivity, this, ViewMode.WRITE)
    }
}
