package com.abubakar.tvshowmanager.util.ext

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showOrHide(show: Boolean) = if (show) show() else hide()