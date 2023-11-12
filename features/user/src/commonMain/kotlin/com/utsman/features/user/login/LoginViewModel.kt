package com.utsman.features.user.login

import com.utsman.api.authentication.AuthenticationRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<LoginState, LoginIntent>(LoginState()) {

    val loginFields = MutableStateFlow(LoginFields())

    override fun sendIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> {
                login()
            }
            is LoginIntent.ToHome -> {
                intent.toHome.invoke()
            }
            is LoginIntent.ToRegister -> {
                intent.toRegister.invoke()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        val loginFieldsValue = loginFields.value
        authenticationRepository.login(loginFieldsValue.user, loginFieldsValue.password)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncLogin = it
                    )
                }
            }
    }
}