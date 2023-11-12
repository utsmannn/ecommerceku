package com.utsman.api.authentication.model

import com.utsman.api.authentication.model.entity.User
import com.utsman.api.authentication.model.response.UserResponse

fun UserResponse.DataResponse.toUser(): User {
    return User(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}