package com.example.koinandktortutorial.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieInfo(
    val movieId: String,
    val movieName: String,
    val movieDesc: String,
    val movieImgUrl: String
) : Response()
