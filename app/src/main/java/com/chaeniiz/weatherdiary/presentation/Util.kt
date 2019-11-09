package com.chaeniiz.weatherdiary.presentation

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
