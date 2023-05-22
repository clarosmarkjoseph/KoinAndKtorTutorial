package com.example.koinandktortutorial.data.repository

import com.example.koinandktortutorial.data.dto.MovieInfo
import com.example.koinandktortutorial.data.dto.MovieListResponse
import com.example.koinandktortutorial.data.dto.MovieRequest
import com.example.koinandktortutorial.data.dto.SubmitRequestResponse
import com.example.koinandktortutorial.domain.NetworkResponse
import com.example.koinandktortutorial.domain.repository.MovieListService
import com.example.koinandktortutorial.utils.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.HttpException
import java.io.IOException

class MovieListServiceImpl(
    private val client: HttpClient? = null
) : MovieListService {

    override suspend fun getMovieList(): Flow<NetworkResponse<List<MovieInfo>?>> = callbackFlow {
        trySend(NetworkResponse.Loading())
        try {
            val httpResponse: MovieListResponse? = client?.request(HttpRoutes.MOVIE_URL) {
                method = HttpMethod.Get
            }?.body()
            trySend(NetworkResponse.Success(httpResponse?.data))
        } catch (e: HttpException) {
            trySend(NetworkResponse.Error(e.localizedMessage ?: "An unexpected error occured."))
        } catch (e: IOException) {
            trySend(NetworkResponse.Error("Couldn't reach to server. Please check your internet"))
        } catch (e: ClientRequestException) {
            trySend(NetworkResponse.Error(e.localizedMessage))
        }
        awaitClose {
            cancel()
        }
    }

    override suspend fun submitMovie(movieRequest: MovieRequest): Flow<NetworkResponse<String?>> =
        callbackFlow {
            trySend(NetworkResponse.Loading())
            try {
                val httpResponse: SubmitRequestResponse? = client?.request(HttpRoutes.SUBMIT_URL) {
                    method = HttpMethod.Post
                    setBody(movieRequest)
                }?.body()
                trySend(NetworkResponse.Success(httpResponse?.refNo))
            } catch (e: HttpException) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "An unexpected error occured."))
            } catch (e: IOException) {
                trySend(NetworkResponse.Error("Couldn't reach to server. Please check your internet"))
            } catch (e: ClientRequestException) {
                trySend(NetworkResponse.Error(e.localizedMessage))
            }
            awaitClose {
                cancel()
            }
        }

}