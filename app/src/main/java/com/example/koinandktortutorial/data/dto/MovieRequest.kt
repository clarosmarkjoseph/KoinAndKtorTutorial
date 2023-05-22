package com.example.koinandktortutorial.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MovieRequest(
    val movieId: String
)
