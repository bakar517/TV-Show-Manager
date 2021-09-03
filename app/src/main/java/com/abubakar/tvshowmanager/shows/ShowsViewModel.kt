package com.abubakar.tvshowmanager.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.navigation.popBackStack
import com.abubakar.tvshowmanager.service.ApiService
import com.abubakar.tvshowmanager.util.ext.formattedDateTime
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowsViewModel @Inject constructor(
    private val service: ApiService,
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableLiveData(ViewState(
        onTapBackButton = { navigator.popBackStack() }
    ))

    val state: LiveData<ViewState>
        get() = _state

    init {
        loadShowList()
    }

    private fun loadShowList() {
        _state.value = _state.value!!.copy(state = ViewState.RequestState.Loading)

        viewModelScope.launch {

            when (val shows = service.tvShows()) {

                is TvShowResult.TvShows -> {

                    val list = shows.data.movies.edges?.map { entry ->

                        val date: String = entry?.node?.releaseDate.toString() ?: ""

                        MovieItem(
                            title = entry?.node?.title,
                            releaseDate = date.formattedDateTime(),
                            seasons = entry?.node?.seasons
                        )
                    } ?: emptyList()

                    _state.value =
                        _state.value!!.copy(
                            state = ViewState.RequestState.Data(list)
                        )
                }

                is TvShowResult.Error -> {
                    _state.value = _state.value!!.copy(state = ViewState.RequestState.Error)
                }
            }
        }

    }
}

data class ViewState(
    val state: RequestState = RequestState.Loading,
    val onTapBackButton: () -> Unit
) {
    sealed class RequestState {
        object Loading : RequestState()
        object Error : RequestState()
        data class Data(val list: List<MovieItem>) : RequestState()
    }
}

data class MovieItem(
    val title: String?,
    val releaseDate: String?,
    val seasons: Double?
)