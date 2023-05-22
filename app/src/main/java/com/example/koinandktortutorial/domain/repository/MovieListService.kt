package com.example.koinandktortutorial.domain.repository

import com.example.koinandktortutorial.data.dto.MovieInfo
import com.example.koinandktortutorial.data.dto.MovieRequest
import com.example.koinandktortutorial.data.dto.SubmitRequestResponse
import com.example.koinandktortutorial.domain.NetworkResponse
import kotlinx.coroutines.flow.Flow

// Ktor way of create service like in retrofit annotation processing (UserApi)
interface MovieListService {

    suspend fun getMovieList(): Flow<NetworkResponse<List<MovieInfo>?>>

    suspend fun submitMovie(movieRequest: MovieRequest): Flow<NetworkResponse<String?>>
}