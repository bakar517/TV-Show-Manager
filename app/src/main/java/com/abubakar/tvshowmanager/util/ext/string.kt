package com.abubakar.tvshowmanager.util.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun String.double(): Double? = runCatching {
    this.toDouble()
}.getOrNull()


@SuppressLint("SimpleDateFormat")
fun String.formattedDateTime(
    currentFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    requiredFormat: String = "dd MMMM yyyy, HH:mm"
): String =
    runCatching {
        val date = SimpleDateFormat(currentFormat).parse(this)
        SimpleDateFormat(requiredFormat).format(date!!)
    }.getOrElse { "" }

@SuppressLint("SimpleDateFormat")
fun String.date(
    currentFormat: String = "yyyy-MM-dd HH:mm",
    requiredFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
): Date? = runCatching {
    val dateTime = formattedDateTime(currentFormat = currentFormat, requiredFormat = requiredFormat)
    SimpleDateFormat(requiredFormat).parse(dateTime)
}.getOrNull()
