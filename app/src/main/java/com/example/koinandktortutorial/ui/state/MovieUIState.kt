package com.example.koinandktortutorial.ui.state

import androidx.annotation.Keep
import com.example.koinandktortutorial.data.dto.MovieInfo


@Keep
sealed class MovieUIState {
    data class OnDisplayData(val data: List<MovieInfo>?) : MovieUIState()
    data class OnSuccessRequest(val data: String?) : MovieUIState()
    data class OnError(val message: String? = "") : MovieUIState()
    object OnLoading : MovieUIState()
}