package com.abubakar.tvshowmanager.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.navigation.popBackStack
import javax.inject.Inject

class DataAndTimePickerViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private var _state = MutableLiveData(defaultViewState())

    val state: LiveData<DateTimeViewState>
        get() = _state

    private fun onTapCancel() {
        _state.value = _state.value!!.copy(time = "", date = "", showDateSelection = false)
        navigator.popBackStack()
    }

    private fun onTapDone(time: String) {
        _state.value = _state.value!!.copy(time = time, showDateSelection = false)

        navigator.navigateUp()

    }

    private fun onBackToDateSelection() {
        _state.value = _state.value!!.copy(showDateSelection = true)
    }

    private fun onDateSelection(date: String) {
        _state.value = _state.value!!.copy(showDateSelection = false, date = date)
    }

    fun resetState() {
        _state = MutableLiveData(defaultViewState())
    }

    private fun defaultViewState() = DateTimeViewState(
        onTapCancel = { onTapCancel() },
        onTapDone = { onTapDone(it) },
        onTapBack = { onBackToDateSelection() },
        onTapNext = { onDateSelection(it) }
    )
}

data class DateTimeViewState(
    val showDateSelection: Boolean = true,
    val date: String = "",
    val time: String = "",
    val onTapBack: () -> Unit,
    val onTapNext: (String) -> Unit,
    val onTapDone: (String) -> Unit,
    val onTapCancel: () -> Unit = {},
)