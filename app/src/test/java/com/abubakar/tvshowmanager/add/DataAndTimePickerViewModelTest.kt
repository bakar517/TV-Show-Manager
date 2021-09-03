package com.abubakar.tvshowmanager.add

import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.BaseTest
import com.abubakar.tvshowmanager.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import org.junit.Rule

import com.google.common.truth.Truth.assertThat


class DataAndTimePickerViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var navigator: Navigator

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }

    @Test
    fun `should navigate on cancel button press`() {

        val viewModelTest = DataAndTimePickerViewModel(navigator)
        val state = viewModelTest.state.getOrAwaitValue()
        state.onTapCancel()
        verify(exactly = 1) { navigator.navigate(any()) }
    }

    @Test
    fun `should update view on next button press`() {

        val viewModelTest = DataAndTimePickerViewModel(navigator)

        val state = viewModelTest.state.getOrAwaitValue()
        state.onTapNext("")
        assertThat(state.showDateSelection).isTrue()
    }

    @Test
    fun `should update view on back button press`() {

        val viewModelTest = DataAndTimePickerViewModel(navigator)

        var state = viewModelTest.state.getOrAwaitValue()
        state.onTapNext("")
        state = viewModelTest.state.getOrAwaitValue()
        state.onTapBack()
        state = viewModelTest.state.getOrAwaitValue()
        assertThat(state.showDateSelection).isTrue()
    }

    @Test
    fun `should update date and time on done button press`() {

        val viewModelTest = DataAndTimePickerViewModel(navigator)
        var state = viewModelTest.state.getOrAwaitValue()
        state.onTapNext("2021-09-03")
        state = viewModelTest.state.getOrAwaitValue()
        state.onTapDone("17:19")
        state = viewModelTest.state.getOrAwaitValue()
        assertThat(state.date).isNotEmpty()
        assertThat(state.time).isNotEmpty()
    }

    @Test
    fun `date and time must not be empty`() {

        val viewModelTest = DataAndTimePickerViewModel(navigator)
        var state = viewModelTest.state.getOrAwaitValue()
        state.onTapNext("2021-09-03")
        state = viewModelTest.state.getOrAwaitValue()
        state.onTapDone("17:19")
        state = viewModelTest.state.getOrAwaitValue()
        assertThat(state.date).isNotEmpty()
        assertThat(state.time).isNotEmpty()
    }

}