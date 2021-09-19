package com.chaeniiz.weatherdiary.presentation.citiesdialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.entity.entities.City
import com.chaeniiz.weatherdiary.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.dialog_cities.*
import org.koin.android.ext.android.inject

class CitiesDialogActivity : AppCompatActivity() {

    companion object {
        const val RESULT_LOCATION = "result_location"
        const val RESULT_WEATHER = "result_weather"
        const val VIEW_MODE = "view_mode"

        fun startForResult(
            activityResultLauncher: ActivityResultLauncher<Intent>,
            activity: Activity,
            viewMode: ViewMode
        ) {
            activityResultLauncher.launch(
                Intent(activity, CitiesDialogActivity::class.java).apply {
                    putExtra(VIEW_MODE, viewMode)
                }
            )
        }
    }

    private val viewModel: CitiesDialogViewModel by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_cities)

        seoulTextView.setOnClickListener { viewModel.getCurrentWeather(City.SEOUL) }
        incheonTextView.setOnClickListener { viewModel.getCurrentWeather(City.INCHEON) }
        daejeonTextView.setOnClickListener { viewModel.getCurrentWeather(City.DAEJEON) }
        gwangjuTextView.setOnClickListener { viewModel.getCurrentWeather(City.GWANGJU) }
        busanTextView.setOnClickListener { viewModel.getCurrentWeather(City.BUSAN) }
        daeguTextView.setOnClickListener { viewModel.getCurrentWeather(City.DAEGU) }
        ulsanTextView.setOnClickListener { viewModel.getCurrentWeather(City.ULSAN) }
        jejuTextView.setOnClickListener { viewModel.getCurrentWeather(City.JEJU) }

        initialize()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun initialize() {
        when (intent.getSerializableExtra(VIEW_MODE) as ViewMode) {
            ViewMode.WRITE -> setDescriptionTextView(current = true)
            ViewMode.EDIT -> setDescriptionTextView(current = false)
        }
    }

    private fun observeState() {
        compositeDisposable += viewModel.state.subscribe { state ->
            when (state) {
                CitiesDialogState.GetCurrentWeatherFailed -> {
                    showErrorToast()
                    finish()
                }
                is CitiesDialogState.GetCurrentWeatherSucceed -> {
                    setResult(
                        location = state.location,
                        weather = state.weather
                    )
                    finish()
                }
                CitiesDialogState.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        dialogProgressBar.visibility = View.VISIBLE
        descriptionTextView.visibility = View.INVISIBLE
        seoulTextView.visibility = View.INVISIBLE
        incheonTextView.visibility = View.INVISIBLE
        daejeonTextView.visibility = View.INVISIBLE
        gwangjuTextView.visibility = View.INVISIBLE
        busanTextView.visibility = View.INVISIBLE
        daeguTextView.visibility = View.INVISIBLE
        ulsanTextView.visibility = View.INVISIBLE
        jejuTextView.visibility = View.INVISIBLE
    }

    private fun setResult(location: String, weather: String) {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(RESULT_LOCATION, location)
                putExtra(RESULT_WEATHER, weather)
            }
        )
    }

    private fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    private fun setDescriptionTextView(current: Boolean) {
        if (current)
            descriptionTextView.text = getString(R.string.city_dialog_description_current)
        else
            descriptionTextView.text = getString(R.string.city_dialog_description_past)
    }
}
