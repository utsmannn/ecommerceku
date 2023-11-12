package com.utsman.features.user

import com.utsman.api.authentication.model.entity.User
import com.utsman.libraries.core.state.Async

data class UserState(
    val asyncUser: Async<User> = Async.Default
)