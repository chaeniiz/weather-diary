package com.chaeniiz.weatherdiary.presentation.write

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.entity.entities.City
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.RequestCode
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.*

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
    lateinit var cityDialog: AlertDialogBuilder

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

    override fun showCityDialog() {
        cityDialog = AlertDialogBuilder(this).apply {
            val dialogView =
                LayoutInflater.from(this@WriteActivity).inflate(R.layout.dialog_cities, null)
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

    override fun dismissCityDialog() {
        if (::cityDialog.isInitialized)
            cityDialog.dismiss()
    }

    override fun setLocationTextView(location: String, weather: String) {
        val text = location.includeCommaAndSpace() + weather
        locationTextView.text = text
    }

    override fun showErrorDialog(emptyContent: Boolean) {
        AlertDialogBuilder(this).apply {
            message(
                if (emptyContent)
                    getString(R.string.error_no_content)
                else
                    getString(R.string.error_no_location)
            )
            positiveButton(getString(R.string.general_dialog_accept)) {
                dismiss()
            }
        }.show()
    }

    override fun showErrorToast() {
        toast(R.string.general_error)
    }
}
