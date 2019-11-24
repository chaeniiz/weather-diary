package com.chaeniiz.weatherdiary.presentation.citiesdialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.entity.entities.City
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import kotlinx.android.synthetic.main.dialog_cities.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class CitiesDialogActivity : AppCompatActivity(), CitiesDialogView {

    companion object {
        const val RESULT_LOCATION = "result_location"
        const val RESULT_WEATHER = "result_weather"
        const val VIEW_MODE = "view_mode"

        fun startForResult(activity: Activity, viewMode: ViewMode) {
            with(activity) {
                startActivityForResult(
                    intentFor<CitiesDialogActivity>().apply {
                        putExtra(VIEW_MODE, viewMode)
                    },
                    RequestCode.CITIES_DIALOG_ACTIVITY_CODE.ordinal
                )
            }
        }
    }

    private val presenter: CitiesDialogPresenter by lazy {
        CitiesDialogPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_cities)

        seoulTextView.onClick { presenter.onCityClicked(City.SEOUL) }
        incheonTextView.onClick { presenter.onCityClicked(City.INCHEON) }
        daejeonTextView.onClick { presenter.onCityClicked(City.DAEJEON) }
        gwangjuTextView.onClick { presenter.onCityClicked(City.GWANGJU) }
        busanTextView.onClick { presenter.onCityClicked(City.BUSAN) }
        daeguTextView.onClick { presenter.onCityClicked(City.DAEGU) }
        ulsanTextView.onClick { presenter.onCityClicked(City.ULSAN) }
        jejuTextView.onClick { presenter.onCityClicked(City.JEJU) }

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
        toast(R.string.general_error)
    }

    override fun setDescriptionTextView(current: Boolean) {
        if (current)
            descriptionTextView.text = getString(R.string.city_dialog_description_current)
        else
            descriptionTextView.text = getString(R.string.city_dialog_description_past)
    }
}
