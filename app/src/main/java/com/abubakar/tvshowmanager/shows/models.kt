package com.abubakar.tvshowmanager.shows

import com.abubakar.tvshowmanager.MoviesListQuery

sealed class TvShowResult {

    data class TvShows(val data: MoviesListQuery.Data) : TvShowResult()

    data class Error(val error: Throwable?) : TvShowResult()
}