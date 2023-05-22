package com.example.koinandktortutorial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinandktortutorial.data.dto.MovieRequest
import com.example.koinandktortutorial.domain.NetworkResponse
import com.example.koinandktortutorial.domain.repository.MovieListService
import com.example.koinandktortutorial.ui.state.MovieUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MovieListViewModel() : ViewModel() {

    private val getMovieListService: MovieListService by inject(
        MovieListService::class.java
    )
    private val _ui2ndScreenState = MutableStateFlow<MovieUIState?>(null)
    val ui2ndScreenState: StateFlow<MovieUIState?> get() = _ui2ndScreenState

    init {
        viewModelScope.launch {
            getMovieListService.getMovieList()
                .collectLatest {
                    when (it) {
                        is NetworkResponse.Success -> {
                            _ui2ndScreenState.value = MovieUIState.OnDisplayData(it.data)
                        }
                        is NetworkResponse.Loading -> {
                            _ui2ndScreenState.value = MovieUIState.OnLoading
                        }
                        else -> {
                            _ui2ndScreenState.value = MovieUIState.OnError(it.message)
                        }
                    }
                }
        }
    }

    fun submitPostRequest() {
        viewModelScope.launch {
            val movieRequest = MovieRequest(
                movieId = "12345"
            )
            getMovieListService.submitMovie(movieRequest).collectLatest {
                when (it) {
                    is NetworkResponse.Success -> {
                        _ui2ndScreenState.value = MovieUIState.OnSuccessRequest(it.data)
                    }
                    is NetworkResponse.Loading -> {
                        _ui2ndScreenState.value = MovieUIState.OnLoading
                    }
                    else -> {
                        _ui2ndScreenState.value = MovieUIState.OnError(it.message)
                    }
                }
            }
        }
    }

}