package screencontainer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.stack.mutableStateStackOf
import splash.SplashScreen
import stacktransition.SlideTransition
import uuid

val mainScreenStack = mutableStateStackOf<Screen>(SplashScreenScreen())

class MainScreen : Screen {

    @Composable
    override fun Content() {
        SlideTransition(mainScreenStack)
    }
}

class SplashScreenScreen : Screen {

    override val key: ScreenKey
        get() = uuid()

    @Composable
    override fun Content() {
        SplashScreen(
            toHome =  {
                mainScreenStack.push(HomePagerScreen())
            }
        )
    }
}