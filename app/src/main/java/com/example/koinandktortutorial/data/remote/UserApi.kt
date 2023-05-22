package com.example.koinandktortutorial.data.remote

import com.example.koinandktortutorial.data.dto.UserListResponse
import retrofit2.http.GET

interface UserApi {

    @GET("/getUserList")
    suspend fun getUserList() : UserListResponse

}