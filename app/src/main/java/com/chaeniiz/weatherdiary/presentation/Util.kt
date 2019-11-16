package com.chaeniiz.weatherdiary.presentation

import com.chaeniiz.entity.entities.Weather
import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(
    form: String,
    locale: Locale = Locale.getDefault(),
    timeZone: TimeZone = TimeZone.getDefault()
): String =
    SimpleDateFormat(form, locale)
        .apply { this.timeZone = timeZone }
        .format(this)
        .toString()

fun String.includeCommaAndSpace(): String =
    "$this, "

fun Weather.convertWeather(): String =
    when (this.id) {
        in 0..299 -> "뇌우"
        in 300..399 -> "이슬비"
        in 400..599 -> "비"
        in 600..699 -> "눈"
        in 700..799 -> "안개"
        800 -> "맑음"
        else -> "구름 많음"
    }
