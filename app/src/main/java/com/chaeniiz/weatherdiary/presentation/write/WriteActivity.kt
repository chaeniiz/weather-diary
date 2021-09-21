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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_write.*
import org.koin.android.ext.android.inject

class WriteActivity : AppCompatActivity() {

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

    private val launcherForCitiesDialogActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.let {
                location = it.getStringExtra(CitiesDialogActivity.RESULT_LOCATION)!!
                weather = it.getStringExtra(CitiesDialogActivity.RESULT_WEATHER)!!
                setLocationTextView(location, weather)
            }
        }
    }

    private val viewModel: WriteViewModel by inject()
    private val compositeDisposable = CompositeDisposable()
    private var location: String = ""
    private var weather: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        writeButton.setOnClickListener {
            viewModel.onWriteButtonClicked(
                location = location,
                weather = weather,
                content = contentEditText.text.toString()
            )
        }

        locationTextView.setOnClickListener {
            startCitiesDialogActivity()
        }

        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun observeState() {
        compositeDisposable += viewModel.state.subscribe { state ->
            when (state) {
                WriteState.Error.EmptyContent -> {
                    showErrorDialog(state)
                }
                WriteState.Error.EmptyLocation -> {
                    showErrorDialog(state)
                }
                WriteState.Error.Server -> {
                    showErrorToast()
                }
                WriteState.WriteDiarySucceed -> {
                    finish()
                }
            }
        }
    }

    private fun setLocationTextView(location: String, weather: String) {
        val text = location.includeCommaAndSpace() + weather
        locationTextView.text = text
    }

    private fun showErrorDialog(error: WriteState) {
        AlertDialog.Builder(this, R.style.WeatherDiaryDialogTheme).apply {
            setMessage(
                if (error is WriteState.Error.EmptyContent)
                    R.string.error_no_content
                else
                    R.string.error_no_location
            )
            setPositiveButton(R.string.general_accept) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    private fun startCitiesDialogActivity() {
        CitiesDialogActivity.startForResult(launcherForCitiesDialogActivity, this, ViewMode.WRITE)
    }
}
