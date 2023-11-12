package com.utsman.features.user.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.utsman.api.authentication.LocalAuthenticationRepository
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.Header
import kotlinx.coroutines.flow.update

@Composable
fun Login(
    toHome: () -> Unit,
    toRegister: () -> Unit
) {
    val authRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { LoginViewModel(authRepository) }
    val state by viewModel.uiState.collectAsState()
    val loginFields by viewModel.loginFields.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login",
            style = TextStyle.Header
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = loginFields.user,
            onValueChange = { value ->
                viewModel.loginFields.update {
                    it.copy(
                        user = value
                    )
                }
            },
            label = { Text("Username") },
            placeholder = { Text("Input username") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp).padding(top = 12.dp),
            value = loginFields.password,
            onValueChange = { value ->
                viewModel.loginFields.update {
                    it.copy(
                        password = value
                    )
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            placeholder = { Text("Input password") }
        )

        Row {
            Button(
                onClick = {
                    viewModel.sendIntent(LoginIntent.Login)
                },
                enabled = state.asyncLogin !is Async.Loading,
                modifier = Modifier.weight(1f).padding(start = 24.dp, top = 12.dp, end = 12.dp)
            ) {
                Text("Login")
            }

            Button(
                onClick = {
                    viewModel.sendIntent(LoginIntent.ToRegister(toRegister))
                },
                enabled = state.asyncLogin !is Async.Loading,
                modifier = Modifier.weight(1f).padding(start = 12.dp, top = 12.dp, end = 24.dp)
            ) {
                Text("Register")
            }
        }

        when (val async = state.asyncLogin) {
            is Async.Loading -> {
                CircularProgressIndicator()
            }
            is Async.Failure -> {
                Failure(
                    modifier = Modifier.fillMaxWidth(),
                    text = async.throwable.message
                )
            }
            is Async.Success -> {
                viewModel.sendIntent(LoginIntent.ToHome(toHome))
            }
            else -> {}
        }

    }
}