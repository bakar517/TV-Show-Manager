package com.abubakar.tvshowmanager.util.ext

import com.abubakar.tvshowmanager.R
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.showErrorOrNot(hasError: Boolean) {
    this.isErrorEnabled = hasError
    if (hasError) {
        this.error = context.getString(R.string.please_enter_title_of_show)
    }
}