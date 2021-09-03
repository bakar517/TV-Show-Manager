package com.abubakar.tvshowmanager.util.ext

import android.os.Build
import android.widget.TimePicker

fun TimePicker.time(): String {
    val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.hour
    } else {
        currentHour
    }
    val minutes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.minute
    } else {
        currentMinute
    }

    return "${hour.twoDigit}:${minutes.twoDigit}"
}