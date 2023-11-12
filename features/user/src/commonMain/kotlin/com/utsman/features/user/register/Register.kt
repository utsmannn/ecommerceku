package com.utsman.features.user.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun Register(
    toLogin: () -> Unit
) {
    val authenticationRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { RegisterViewModel(authenticationRepository) }
    val state by viewModel.uiState.collectAsState()
    val registerFields by viewModel.registerFields.collectAsState()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Register",
                style = TextStyle.Header
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = registerFields.user,
                onValueChange = { value ->
                    viewModel.registerFields.update {
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
                value = registerFields.password,
                onValueChange = { value ->
                    viewModel.registerFields.update {
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
                        viewModel.sendIntent(RegisterIntent.Register)
                    },
                    enabled = state.asyncRegister !is Async.Loading,
                    modifier = Modifier.weight(1f).padding(start = 24.dp, top = 12.dp, end = 12.dp)
                ) {
                    Text("Register")
                }

                Button(
                    onClick = {
                        viewModel.sendIntent(RegisterIntent.ToLogin(toLogin))
                    },
                    enabled = state.asyncRegister !is Async.Loading,
                    modifier = Modifier.weight(1f).padding(start = 12.dp, top = 12.dp, end = 24.dp)
                ) {
                    Text("Back to login")
                }
            }

            when (val async = state.asyncRegister) {
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
                    scope.launch {
                        viewModel.sendIntent(
                            RegisterIntent.SnackbarSuccess(
                                scaffoldState = scaffoldState,
                                scope = scope,
                                text = "Register success"
                            )
                        )
                        delay(1000)
                        viewModel.sendIntent(
                            RegisterIntent.ToLogin(toLogin)
                        )
                    }
                }
                else -> {}
            }

        }
    }

}