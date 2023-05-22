package com.example.koinandktortutorial.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val data: List<MovieInfo>?
) : Response()
