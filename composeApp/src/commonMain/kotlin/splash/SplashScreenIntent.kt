package splash

import com.utsman.libraries.core.state.Intent

sealed class SplashScreenIntent : Intent {
    data object GetCurrentUser : SplashScreenIntent()
    data class ToHome(val toHome: () -> Unit) : SplashScreenIntent()
    data class ToLogin(val toLogin: () -> Unit) : SplashScreenIntent()
}