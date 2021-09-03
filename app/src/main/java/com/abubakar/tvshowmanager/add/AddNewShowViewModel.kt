package com.abubakar.tvshowmanager.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.navigation.popBackStack
import com.abubakar.tvshowmanager.service.ApiService
import com.abubakar.tvshowmanager.type.CreateMovieFieldsInput
import com.abubakar.tvshowmanager.util.ext.date
import com.abubakar.tvshowmanager.util.ext.double
import com.apollographql.apollo.api.Input
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddNewShowViewModel @Inject constructor(
    private val service: ApiService,
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableLiveData(ViewState(
        onTapBackButton = { navigator.popBackStack() },
        onDateTimePick = { navigator.goToDateTimePicker() },
        onTapSave = { insertItem(it) }
    ))

    val state: LiveData<ViewState>
        get() = _state

    private fun insertItem(form: MovieForm) {
        if (form.title.isEmpty()) {
            _state.value = _state.value!!.copy(showErrorOfTitleEmpty = true)
            return
        }
        _state.value = _state.value!!.copy(showErrorOfTitleEmpty = false)

        val dateTimeInfo = _state.value!!.dateTimeInfo
        val dateTime = "${dateTimeInfo?.date} ${dateTimeInfo?.time}"
        insertItem(
            title = form.title,
            season = form.season.double(),
            releaseDate = dateTime.date()
        )
    }

    private fun insertItem(title: String, season: Double?, releaseDate: Date?) {

        _state.value = _state.value!!.copy(state = ViewState.InsertionRequestState.Loading)

        val fields = CreateMovieFieldsInput(
            title = title,
            releaseDate = Input.fromNullable(releaseDate),
            seasons = Input.fromNullable(season ?: 1.0)
        )

        viewModelScope.launch {
            when (val result = service.insertShow(fields)) {

                is ShowInsertionResult.Show -> {
                    if (result.data.createMovie == null) {
                        _state.value =
                            _state.value!!.copy(state = ViewState.InsertionRequestState.Error)
                        return@launch
                    }
                    _state.value = _state.value!!.copy(
                        state = ViewState.InsertionRequestState.Data(
                            movie = Movie(
                                title = result.data.createMovie.movie.title,
                                releaseDate = result.data.createMovie.movie.releaseDate.toString(),
                                season = result.data.createMovie.movie.seasons
                            )
                        )
                    )
                    navigator.navigateUp()
                }

                is ShowInsertionResult.Error -> {
                    _state.value =
                        _state.value!!.copy(state = ViewState.InsertionRequestState.Error)

                }
            }

        }

    }

    fun updateDateTime(date: String, title: String) {
        _state.value = _state.value!!.copy(dateTimeInfo = ViewState.DateTimeInfo(date, title))
    }
}

data class ViewState(
    val state: InsertionRequestState = InsertionRequestState.Noting,
    val showErrorOfTitleEmpty: Boolean = false,
    val onTapBackButton: () -> Unit,
    val onDateTimePick: () -> Unit,
    val onTapSave: (MovieForm) -> Unit = {},
    val dateTimeInfo: DateTimeInfo? = null,
) {
    sealed class InsertionRequestState {
        object Noting : InsertionRequestState()
        object Loading : InsertionRequestState()
        object Error : InsertionRequestState()
        data class Data(val movie: Movie) : InsertionRequestState()
    }

    data class DateTimeInfo(val date: String, val time: String)
}


data class Movie(val title: String, val season: Double?, val releaseDate: String?)

data class MovieForm(val title: String, val season: String)