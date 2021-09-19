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
import kotlinx.android.synthetic.main.dialog_cities.*

class CitiesDialogActivity : AppCompatActivity(), CitiesDialogView {

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

    private val presenter: CitiesDialogPresenter by lazy {
        CitiesDialogPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_cities)

        seoulTextView.setOnClickListener { presenter.onCityClicked(City.SEOUL) }
        incheonTextView.setOnClickListener { presenter.onCityClicked(City.INCHEON) }
        daejeonTextView.setOnClickListener { presenter.onCityClicked(City.DAEJEON) }
        gwangjuTextView.setOnClickListener { presenter.onCityClicked(City.GWANGJU) }
        busanTextView.setOnClickListener { presenter.onCityClicked(City.BUSAN) }
        daeguTextView.setOnClickListener { presenter.onCityClicked(City.DAEGU) }
        ulsanTextView.setOnClickListener { presenter.onCityClicked(City.ULSAN) }
        jejuTextView.setOnClickListener { presenter.onCityClicked(City.JEJU) }

        presenter.onCreate(intent.getSerializableExtra(VIEW_MODE) as ViewMode)
    }

    override fun showProgressBar() {
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

    override fun setResult(location: String, weather: String) {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(RESULT_LOCATION, location)
                putExtra(RESULT_WEATHER, weather)
            }
        )
    }

    override fun showErrorToast() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    override fun setDescriptionTextView(current: Boolean) {
        if (current)
            descriptionTextView.text = getString(R.string.city_dialog_description_current)
        else
            descriptionTextView.text = getString(R.string.city_dialog_description_past)
    }
}
