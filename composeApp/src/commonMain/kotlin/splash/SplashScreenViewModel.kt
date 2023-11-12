package splash

import com.utsman.api.authentication.AuthenticationRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<SplashScreenState, SplashScreenIntent>(SplashScreenState()) {
    override fun sendIntent(intent: SplashScreenIntent) {
        when (intent) {
            is SplashScreenIntent.GetCurrentUser -> getCurrentUser()
            is SplashScreenIntent.ToHome -> intent.toHome.invoke()
            is SplashScreenIntent.ToLogin -> intent.toLogin.invoke()
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        authenticationRepository.getCurrentUser()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncUser = it
                    )
                }
            }
    }
}