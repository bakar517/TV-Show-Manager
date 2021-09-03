package com.abubakar.tvshowmanager.di


import com.abubakar.tvshowmanager.CreateMovieMutation
import com.abubakar.tvshowmanager.MoviesListQuery
import com.abubakar.tvshowmanager.add.ShowInsertionResult
import com.abubakar.tvshowmanager.shows.TvShowResult
import com.abubakar.tvshowmanager.service.ApiService
import com.abubakar.tvshowmanager.type.CreateMovieFieldsInput


class MockApiService : ApiService {
    override suspend fun tvShows(): TvShowResult {
        return TvShowResult.TvShows(
            data = MoviesListQuery.Data(
                movies = MoviesListQuery.Movies(
                    edges = listOf(
                        MoviesListQuery.Edge(
                            node = MoviesListQuery.Node(
                                title = "Test1",
                                releaseDate = "2021-08-11T00:00:00.000Z",
                                seasons = 1.0
                            )
                        ),
                        MoviesListQuery.Edge(
                            node = MoviesListQuery.Node(
                                title = "Test2",
                                releaseDate = "2021-08-11T00:00:00.000Z",
                                seasons = 2.0
                            )
                        )
                    )
                )
            )
        )
    }

    override suspend fun insertShow(input: CreateMovieFieldsInput): ShowInsertionResult {
        return ShowInsertionResult.Show(
            data = CreateMovieMutation.Data(
                createMovie = CreateMovieMutation.CreateMovie(
                    movie = CreateMovieMutation.Movie(
                        title = input.title,
                        seasons = input.seasons.value,
                        releaseDate = input.releaseDate.value
                    ),
                    clientMutationId = null
                )
            )
        )
    }


}