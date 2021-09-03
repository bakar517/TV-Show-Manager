package com.abubakar.tvshowmanager.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.BaseTest
import com.abubakar.tvshowmanager.CreateMovieMutation
import com.abubakar.tvshowmanager.getOrAwaitValue
import com.abubakar.tvshowmanager.service.ApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AddNewShowViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var navigator: Navigator

    @MockK
    lateinit var service: ApiService

    @MockK
    lateinit var form: MovieForm

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }


    @Test
    fun `should navigate on back button press`() {
        val testViewModel = AddNewShowViewModel(service, navigator)
        val state = testViewModel.state.getOrAwaitValue()
        state.onTapBackButton()
        verify(exactly = 1) { navigator.navigate(any()) }
    }

    @Test
    fun `validation of form`() {
        val testViewModel = AddNewShowViewModel(service, navigator)
        val state = testViewModel.state.getOrAwaitValue()
        state.onTapBackButton()
        verify(exactly = 1) { navigator.navigate(any()) }
    }


    @Test
    fun `must save information on server`() = runBlockingTest {
        every { form.title }.returns("Test1")
        coEvery { service.insertShow(any()) }.returns(successResponse(title = form.title))
        val testViewModel = AddNewShowViewModel(service, navigator)
        var state = testViewModel.state.getOrAwaitValue()
        state.onTapSave(form)
        state = testViewModel.state.getOrAwaitValue()
        assertThat(state.showErrorOfTitleEmpty).isFalse()
        assertThat(state.state is ViewState.InsertionRequestState.Data).isTrue()
        assertThat((state.state as ViewState.InsertionRequestState.Data).movie.title).isNotNull()
        assertThat((state.state as ViewState.InsertionRequestState.Data).movie.title).isNotEmpty()
        assertThat((state.state as ViewState.InsertionRequestState.Data).movie.title).isEqualTo("Test1")
        verify(exactly = 1) { navigator.navigateUp()  }
    }


    @Test
    fun `an Error tp save information on server`() = runBlockingTest {
        coEvery { service.insertShow(any()) }.returns(error())
        every { form.title }.returns("test1")
        val testViewModel = AddNewShowViewModel(service, navigator)
        var state = testViewModel.state.getOrAwaitValue()
        state.onTapSave(form)
        state = testViewModel.state.getOrAwaitValue()
        assertThat(state.showErrorOfTitleEmpty).isFalse()
        assertThat(state.state is ViewState.InsertionRequestState.Error).isTrue()
    }

    @Test
    fun `should title of show must be not empty`() = runBlockingTest {
        coEvery { service.insertShow(any()) }.returns(error())
        every { form.title }.returns("")
        val testViewModel = AddNewShowViewModel(service, navigator)
        var state = testViewModel.state.getOrAwaitValue()
        state.onTapSave(form)
        state = testViewModel.state.getOrAwaitValue()
        assertThat(state.showErrorOfTitleEmpty).isTrue()
    }


    private fun error(): ShowInsertionResult =
        ShowInsertionResult.Error(Exception("Mock exception!!"))

    private fun successResponse(
        title: String,
        season: Double? = null,
        releaseDate: String? = null
    ): ShowInsertionResult {
        return ShowInsertionResult.Show(
            data = CreateMovieMutation.Data(
                createMovie = CreateMovieMutation.CreateMovie(
                    movie = CreateMovieMutation.Movie(
                        title = title,
                        seasons = season,
                        releaseDate = releaseDate
                    ),
                    clientMutationId = null
                )
            )
        )
    }


}
