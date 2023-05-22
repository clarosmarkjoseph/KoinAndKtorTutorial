package com.example.koinandktortutorial.domain.di

import android.util.Log
import com.example.koinandktortutorial.data.dto.MovieListResponse
import com.example.koinandktortutorial.data.repository.MovieListServiceImpl
import com.example.koinandktortutorial.domain.repository.MovieListService
import com.example.koinandktortutorial.utils.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val ktorModule = module {

    factory<HttpClient> {
        HttpClient(CIO) {
            defaultRequest {
                url(HttpRoutes.BASE_URL)
            }
            followRedirects = false
            install(ContentNegotiation) {
//                Gson()
                json(
                    Json {
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
//                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("TAG LOGGER", message)
                    }
                }
            }
            HttpResponseValidator {
                validateResponse { response ->
                    Log.i("TAG validateResponse", response.toString())
                    when (val statusCode = response.status.value) {
                        in 300..399 -> {
                            println("StatusCode: $statusCode")
                        }
                        in 400..499 -> {
                            println("StatusCode: $statusCode")
                            throw ClientRequestException(response, "Error $statusCode")
                        }
                        in 200..299->{

                        }
//                        else -> throw ClientRequestException(response, "Error $statusCode")
                    }
//                    val error: Error = response.body()
//                    if (response.status.value != 0) {
////                        throw CustomResponseException(response, "Code: ${error.code}, message: ${error.message}")
//                    }
                }
                handleResponseExceptionWithRequest { exception, _ ->
                    println(exception.message)
                }
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.i("REsponse: ", response.body<MovieListResponse>().toString())
//                    Log.i("REsponse: ", Gson().toJson(response.body()))
                }
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                parameter("api_key","")
            }
        }
    }

    single<MovieListService> {
        MovieListServiceImpl(
            client = get()
        )
    }


}


