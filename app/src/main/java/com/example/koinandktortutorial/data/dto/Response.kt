package com.example.koinandktortutorial.data.dto

import kotlinx.serialization.Serializable

@Serializable
open class Response {
    val responseCode: Int? = null
    val responseMessage: String? = null
}

