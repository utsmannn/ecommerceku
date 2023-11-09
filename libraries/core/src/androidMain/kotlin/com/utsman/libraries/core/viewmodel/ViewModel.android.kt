package com.utsman.libraries.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope as androidViewModelScope

actual abstract class ViewModelPlatform : androidx.lifecycle.ViewModel() {
    actual val viewModelScope = androidViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }

    actual fun clear() {
        onCleared()
    }
}

@Composable
actual fun <T: ViewModel<*, *>> rememberViewModel(isRetain: Boolean, viewModel: () -> T): T {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val host = LocalViewModelHost.current
    val vm = remember {
        if (isRetain) {
            host.getViewModel(viewModel.invoke())
        } else {
            viewModel.invoke()
        }
    }
    DisposableEffect(lifecycle) {
        onDispose {
            vm.clear()
        }
    }

    return vm
}