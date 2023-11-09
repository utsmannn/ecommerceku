package com.utsman.libraries.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

actual abstract class ViewModelPlatform {
    actual val viewModelScope: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = SupervisorJob() + Dispatchers.Main
    }

    protected actual open fun onCleared() {
        viewModelScope.cancel()
    }

    actual fun clear() {
        onCleared()
    }
}

@Composable
actual fun <T: ViewModel<*, *>> rememberViewModel(viewModel: () -> T): T {
    val host = LocalViewModelHost.current
    val vm = remember {
        host.getViewModel(viewModel.invoke())
    }
    DisposableEffect(vm) {
        onDispose {
            vm.clear()
        }
    }

    return vm
}