package com.utsman.api.authentication.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PostLogin(
    val name: String,
    val password: String
)