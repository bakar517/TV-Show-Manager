package com.abubakar.tvshowmanager.add

import com.abubakar.tvshowmanager.CreateMovieMutation

sealed class ShowInsertionResult {

    data class Show(val data: CreateMovieMutation.Data) : ShowInsertionResult()

    data class Error(val error: Throwable?) : ShowInsertionResult()
}