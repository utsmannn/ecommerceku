import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.utsman.features.detail.Detail
import com.utsman.features.home.ui.Home

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Home {
            navigator.push(ScreenRegistry.get(ScreenRoute.Detail(it)))
        }
    }
}

data class DetailScreen(
    val productId: Int
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Detail(productId) {
            navigator.pop()
        }
    }

}