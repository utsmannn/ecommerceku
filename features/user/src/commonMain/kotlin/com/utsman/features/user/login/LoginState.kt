package com.utsman.features.user.login

import com.utsman.libraries.core.state.Async

data class LoginState(
    val asyncLogin: Async<Unit> = Async.Default,
    val user: String = "",
    val password: String = ""
)