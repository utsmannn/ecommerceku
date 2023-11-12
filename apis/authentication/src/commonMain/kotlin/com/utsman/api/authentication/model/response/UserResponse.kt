package com.utsman.api.authentication.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: DataResponse?
) {
    @Serializable
    data class DataResponse(
        @SerialName("id")
        val id: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("created_at")
        val createdAt: String?,
        @SerialName("updated_at")
        val updatedAt: String?
    )
}