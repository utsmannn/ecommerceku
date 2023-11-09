import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.features.detail.Detail
import com.utsman.features.home.ui.Home
import com.utsman.libraries.core.viewmodel.LocalViewModelHost
import com.utsman.libraries.core.viewmodel.ViewModelHost

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val productNetworkDataSources = ProductNetworkDataSource()
    val productRepository = remember { ProductRepository(productNetworkDataSources) }

    CompositionLocalProvider(
        LocalProductRepository provides productRepository,
        LocalViewModelHost provides viewModelHost
    ) {

        ScreenRegistry {
            register<ScreenRoute.Detail> {
                screenOf("detail") {
                    Detail(it.productId)
                }
            }
            register<ScreenRoute.Home> {
                screenOf("home") {
                    val navigator = LocalNavigator.currentOrThrow
                    Home {
                        navigator.push(get(ScreenRoute.Detail(it)))
                    }
                }
            }
        }

        MaterialTheme {
            Navigator(
                screen = ScreenRegistry.get(ScreenRoute.Home)
            ) {
                SlideTransition(it)
            }
        }
    }
}

fun screenOf(key: String, content: @Composable () -> Unit) : Screen {
    return object : Screen {
        override val key: ScreenKey
            get() = key

        @Composable
        override fun Content() {
            content.invoke()
        }
    }
}

sealed class ScreenRoute : ScreenProvider {
    data object Home : ScreenRoute()
    data class Detail(val productId: Int) : ScreenRoute()
}