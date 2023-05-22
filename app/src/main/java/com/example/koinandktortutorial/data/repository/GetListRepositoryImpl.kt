package com.example.koinandktortutorial.data.repository

import android.util.Log
import com.example.koinandktortutorial.data.remote.UserApi
import com.example.koinandktortutorial.data.dto.UserListResponse
import com.example.koinandktortutorial.domain.repository.GetListRepository

class GetListRepositoryImpl(private val userApi: UserApi) : GetListRepository {

    override suspend fun getList(): UserListResponse {
        return userApi.getUserList()
    }

    override fun displaySomething() {
        Log.e("TAG: ", "Display Something")
    }

}