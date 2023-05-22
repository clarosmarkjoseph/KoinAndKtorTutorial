package com.example.koinandktortutorial.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(
    val data: List<UserInfo>?
) : Response()