package com.utsman.features.user.register

import com.utsman.libraries.core.state.Async

data class RegisterState(
    val asyncRegister: Async<Unit> = Async.Default
)