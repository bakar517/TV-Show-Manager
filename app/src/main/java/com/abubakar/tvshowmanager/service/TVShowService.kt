package com.abubakar.tvshowmanager.service

import com.abubakar.baselib.ErrorLog
import com.abubakar.tvshowmanager.CreateMovieMutation
import com.abubakar.tvshowmanager.MoviesListQuery
import com.abubakar.tvshowmanager.add.ShowInsertionResult
import com.abubakar.tvshowmanager.di.Dispatchers
import com.abubakar.tvshowmanager.shows.TvShowResult
import com.abubakar.tvshowmanager.type.CreateMovieFieldsInput
import com.abubakar.tvshowmanager.type.CreateMovieInput
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.invoke
import javax.inject.Inject

open class TVShowService @Inject constructor(
    private val apolloClient: ApolloClient,
    private val dispatchers: Dispatchers,
    private val errorLog: ErrorLog,
) : ApiService {

    override suspend fun tvShows() = dispatchers.IO {

        runCatching {
            val movies = apolloClient.query(MoviesListQuery()).await()
            TvShowResult.TvShows(movies.data!!)
        }.getOrElse {
            errorLog.log(it)
            TvShowResult.Error(it)
        }
    }

    override suspend fun insertShow(input: CreateMovieFieldsInput) = dispatchers.IO {
        runCatching {
            val mutate = CreateMovieMutation(CreateMovieInput(fields = Input.fromNullable(input)))
            val result = apolloClient.mutate(mutate).await()
            ShowInsertionResult.Show(result.data!!)
        }.getOrElse {
            errorLog.log(it)
            ShowInsertionResult.Error(it)
        }
    }


}