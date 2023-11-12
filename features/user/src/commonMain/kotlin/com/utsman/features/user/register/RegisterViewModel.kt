package com.utsman.features.user.register

import androidx.compose.runtime.mutableStateOf
import com.utsman.api.authentication.AuthenticationRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<RegisterState, RegisterIntent>(RegisterState()) {

    val registerFields = MutableStateFlow(RegisterFields())

    override fun sendIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.Register -> {
                register()
            }
            is RegisterIntent.ToLogin -> {
                intent.toLogin.invoke()
            }
            is RegisterIntent.SnackbarSuccess -> {
                intent.scope.launch {
                    intent.scaffoldState.snackbarHostState
                        .showSnackbar(intent.text)
                }
            }
        }
    }

    private fun register() = viewModelScope.launch {
        val registerFieldsValue = registerFields.value
        authenticationRepository.register(
            registerFieldsValue.user, registerFieldsValue.password
        ).stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncRegister = it
                    )
                }
            }
    }
}