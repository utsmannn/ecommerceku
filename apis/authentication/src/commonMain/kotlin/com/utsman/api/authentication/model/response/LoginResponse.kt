package com.utsman.api.authentication.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: DataResponse?
) {
    @Serializable
    data class DataResponse(
        @SerialName("token")
        val token: String?
    )
}