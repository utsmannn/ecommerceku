package com.utsman.features.user

import com.utsman.api.authentication.AuthenticationRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<UserState, UserIntent>(UserState()) {
    override fun sendIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.GetUser -> getUser()
        }
    }

    private fun getUser() = viewModelScope.launch {
        authenticationRepository.getCurrentUser()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncUser = it)
                }
            }
    }
}