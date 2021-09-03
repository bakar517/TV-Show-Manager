package com.abubakar.tvshowmanager.util.ext

val Int.twoDigit: String
    get() = String.format("%02d", this)