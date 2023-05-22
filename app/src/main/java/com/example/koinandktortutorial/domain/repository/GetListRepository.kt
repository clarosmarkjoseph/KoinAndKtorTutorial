package com.example.koinandktortutorial.domain.repository

import com.example.koinandktortutorial.data.dto.UserListResponse

interface GetListRepository {

    suspend fun getList(): UserListResponse

    fun displaySomething()
}