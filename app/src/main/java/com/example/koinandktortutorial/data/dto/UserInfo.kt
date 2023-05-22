package com.example.koinandktortutorial.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: String,
    val name: String
)
