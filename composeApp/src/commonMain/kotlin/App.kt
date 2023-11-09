import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.apis.product.datasources.ProductPagingSources
import com.utsman.libraries.core.viewmodel.LocalViewModelHost
import com.utsman.libraries.core.viewmodel.ViewModelHost

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val productNetworkDataSources = remember { ProductNetworkDataSource() }
    val productPagingSources = remember { ProductPagingSources(productNetworkDataSources) }
    val productRepository = remember { ProductRepository(productNetworkDataSources, productPagingSources) }

    CompositionLocalProvider(
        LocalProductRepository provides productRepository,
        LocalViewModelHost provides viewModelHost
    ) {

        ScreenRegistry {
            register<ScreenRoute.Detail> {
               DetailScreen(it.productId)
            }
            register<ScreenRoute.Home> {
                HomeScreen()
            }
        }

        MaterialTheme {
            Navigator(
                screen = ScreenRegistry.get(ScreenRoute.Home)
            ) {
                VoyagerSwipeable(it)
//                SlideTransition(it)
            }
        }
    }
}