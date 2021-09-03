package com.abubakar.tvshowmanager.util

import com.abubakar.baselib.ErrorLog
import timber.log.Timber
import javax.inject.Inject

class ErrorLogImp @Inject constructor() : ErrorLog {

    override fun log(ex: Throwable) {
        Timber.d(ex)
    }

}