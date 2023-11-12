package splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.utsman.api.authentication.LocalAuthenticationRepository
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel

@Composable
fun SplashScreen(
    toHome: (isLoggedIn: Boolean) -> Unit
) {
    val authRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { SplashScreenViewModel(authRepository) }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(SplashScreenIntent.GetCurrentUser)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when (state.asyncUser) {
            is Async.Loading -> {
                CircularProgressIndicator()
            }
            is Async.Failure -> {
                viewModel.sendIntent(SplashScreenIntent.ToHome(false, toHome))
            }
            is Async.Success -> {
                viewModel.sendIntent(SplashScreenIntent.ToHome(true, toHome))
            }
            else -> {}
        }

    }
}