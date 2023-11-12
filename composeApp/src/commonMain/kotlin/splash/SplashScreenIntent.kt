package splash

import com.utsman.libraries.core.state.Intent

sealed class SplashScreenIntent : Intent {
    data object GetCurrentUser : SplashScreenIntent()
    data class ToHome(val isLoggedIn: Boolean, val toHome: (isLoggedIn: Boolean) -> Unit) : SplashScreenIntent()
}