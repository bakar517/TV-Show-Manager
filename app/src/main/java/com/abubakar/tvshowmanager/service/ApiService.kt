package com.abubakar.tvshowmanager.service

import com.abubakar.tvshowmanager.add.ShowInsertionResult
import com.abubakar.tvshowmanager.shows.TvShowResult
import com.abubakar.tvshowmanager.type.CreateMovieFieldsInput


interface ApiService {

    suspend fun tvShows(): TvShowResult

    suspend fun insertShow(input: CreateMovieFieldsInput): ShowInsertionResult

}