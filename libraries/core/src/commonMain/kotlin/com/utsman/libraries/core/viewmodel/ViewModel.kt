package com.utsman.libraries.core.viewmodel

import androidx.compose.runtime.Composable
import com.utsman.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    abstract fun sendIntent(intent: I)

    protected fun updateUiState(block: S.() -> S) {
        _uiState.update(block)
    }
}

@Composable
expect fun <T: ViewModel<*, *>> rememberViewModel(isRetain: Boolean = true, viewModel: () -> T): T