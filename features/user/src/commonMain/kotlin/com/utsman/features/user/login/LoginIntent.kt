package com.utsman.features.user.login

import com.utsman.libraries.core.state.Intent

sealed class LoginIntent : Intent {
    data object Login : LoginIntent()
    data class ToHome(val toHome: () -> Unit) : LoginIntent()
    data class ToRegister(val toRegister: () -> Unit) : LoginIntent()
}