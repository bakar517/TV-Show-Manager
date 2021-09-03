package com.abubakar.tvshowmanager.util.ext

import android.widget.DatePicker

fun DatePicker.date(): String {
    val day = this.dayOfMonth
    val month = this.month + 1
    val year = this.year

    return "$year-${month.twoDigit}-${day.twoDigit}"
}