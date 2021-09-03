package com.abubakar.tvshowmanager.shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.BaseTest
import com.abubakar.tvshowmanager.MoviesListQuery
import com.abubakar.tvshowmanager.getOrAwaitValue
import com.abubakar.tvshowmanager.service.ApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShowsViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var navigator: Navigator

    @MockK
    lateinit var service: ApiService

    private lateinit var viewModelTest: ShowsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should return the list of movies`() = runBlockingTest {
        coEvery { service.tvShows() }.returns(movies())

        viewModelTest = ShowsViewModel(
            service = service,
            navigator = navigator
        )

        val state = viewModelTest.state.getOrAwaitValue()

        val requestState = state.state
        assertThat(requestState is ViewState.RequestState.Data).isTrue()
        assertThat((requestState as ViewState.RequestState.Data).list.size).isEqualTo(3)
        assertThat(requestState.list[0].title).isEqualTo("Test1")
        assertThat(requestState.list[0].seasons).isEqualTo(1)
    }


    @Test
    fun `should error when fetch list of movies`() = runBlockingTest {
        coEvery { service.tvShows() }.returns(error())

        viewModelTest = ShowsViewModel(
            service = service,
            navigator = navigator
        )

        val state = viewModelTest.state.getOrAwaitValue()
        assertThat(state.state is ViewState.RequestState.Error).isTrue()
    }

    @Test
    fun `should navigator call on back button press`() = runBlockingTest {
        coEvery { service.tvShows() }.returns(movies())

        viewModelTest = ShowsViewModel(
            service = service,
            navigator = navigator
        )

        val state = viewModelTest.state.getOrAwaitValue()
        state.onTapBackButton()
        verify(exactly = 1) { navigator.navigate(any()) }
    }


    private fun movies(): TvShowResult {
        return TvShowResult.TvShows(
            data = MoviesListQuery.Data(
                movies = MoviesListQuery.Movies(
                    edges = listOf(
                        show(1.0, "Test1"),
                        show(1.0, "Test2"),
                        show(2.0, "Test3"),
                    )
                )
            )
        )
    }

    private fun error(): TvShowResult = TvShowResult.Error(Exception("Mock exception!!"))

    private fun show(seasons: Double, title: String): MoviesListQuery.Edge = MoviesListQuery.Edge(
        node = MoviesListQuery.Node(
            title = title,
            releaseDate = "2021-08-11T00:00:00.000Z",
            seasons = seasons
        )
    )
}