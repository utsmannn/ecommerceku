import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.datasources.ProductNetworkDataSource
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
        MaterialTheme {
            Home()
        }
    }

}