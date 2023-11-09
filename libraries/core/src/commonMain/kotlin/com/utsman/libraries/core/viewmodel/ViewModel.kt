package com.utsman.libraries.core.viewmodel

import androidx.compose.runtime.Composable
import com.utsman.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

expect abstract class ViewModelPlatform() {
    val viewModelScope: CoroutineScope

    protected open fun onCleared()
    fun clear()
}

abstract class ViewModel<S: Any, I: Intent>(initialState: S) : ViewModelPlatform() {
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState

    private val _intent: MutableStateFlow<Intent> = MutableStateFlow(Intent.Idle)
    val intent: MutableStateFlow<Intent> = _intent

    fun sendIntent(intent: I) = viewModelScope.launch {
        _intent.value = intent
        delay(80)
        _intent.value = Intent.Idle
    }

    protected fun updateUiState(block: S.() -> S) {
        val currentUiState = _uiState.value
        val newUiState = block.invoke(currentUiState)
        _uiState.value = newUiState
    }
}

@Composable
expect fun <T: ViewModel<*, *>> rememberViewModel(isRetain: Boolean = true, viewModel: () -> T): T