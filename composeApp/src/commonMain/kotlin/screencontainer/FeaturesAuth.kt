package screencontainer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.utsman.features.user.login.Login
import com.utsman.features.user.register.Register
import uuid

class LoginScreen : Screen {

    override val key: ScreenKey
        get() = uuid()

    @Composable
    override fun Content() {
        Login(
            toHome = {
                mainScreenStack.push(HomePagerScreen())
            },
            toRegister = {
                mainScreenStack.push(RegisterScreen())
            }
        )
    }

}

class RegisterScreen : Screen {
    override val key: ScreenKey
        get() = uuid()

    @Composable
    override fun Content() {
        Register {
            mainScreenStack.pop()
        }
    }
}