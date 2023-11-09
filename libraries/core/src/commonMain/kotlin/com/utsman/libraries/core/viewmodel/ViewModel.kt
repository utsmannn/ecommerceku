package com.utsman.libraries.core.viewmodel

import androidx.compose.runtime.Composable
import com.utsman.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

expect abstract class ViewModelPlatform() {
    val viewModelScope: CoroutineScope

    protected open fun onCleared()
    fun clear()
}

abstract class ViewModel<S: Any, I: Intent>(initialState: S) : ViewModelPlatform() {
    private val _homeUiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val homeUiState: StateFlow<S> = _homeUiState

    private val _homeIntent: MutableStateFlow<Intent> = MutableStateFlow(Intent.Idle)
    val homeIntent: MutableStateFlow<Intent> = _homeIntent

    fun sendIntent(intent: I) = viewModelScope.launch {
        _homeIntent.value = intent
        delay(80)
        _homeIntent.value = Intent.Idle
    }

    protected fun updateUiState(block: S.() -> S) {
        val currentUiState = _homeUiState.value
        val newUiState = block.invoke(currentUiState)
        _homeUiState.value = newUiState
    }
}

@Composable
expect fun <T: ViewModel<*, *>> rememberViewModel(viewModel: () -> T): T