package com.chaeniiz.weatherdiary.presentation.diary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chaeniiz.entity.entities.City
import com.chaeniiz.weatherdiary.R
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import kotlinx.android.synthetic.main.activity_diary.*
import org.jetbrains.anko.*

class DiaryActivity : AppCompatActivity(), DiaryView {

    companion object {
        const val KEY_DIARY_ID = "diary_id"

        fun start(context: Context, id: Int) {
            context.run {
                startActivity(
                    intentFor<DiaryActivity>(
                        KEY_DIARY_ID to id
                    )
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

    override fun setContentTextView(content: String) {
        contentEditText.setText(content)
    }

    override fun showCityDialog() {
        cityDialog = AlertDialogBuilder(this).apply {
            val dialogView =
                LayoutInflater.from(this@DiaryActivity).inflate(R.layout.dialog_cities, null)
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

    override fun showErrorToast() {
        toast(R.string.general_error)
    }
}
