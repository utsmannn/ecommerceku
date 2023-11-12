package com.utsman.features.user.register

import androidx.compose.material.ScaffoldState
import com.utsman.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope

sealed class RegisterIntent : Intent {
    data object Register : RegisterIntent()
    data class ToLogin(val toLogin: () -> Unit) : RegisterIntent()
    data class SnackbarSuccess(
        val scaffoldState: ScaffoldState,
        val scope: CoroutineScope,
        val text: String
    ) : RegisterIntent()
}