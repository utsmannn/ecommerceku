import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import com.utsman.api.authentication.AuthenticationRepository
import com.utsman.api.authentication.LocalAuthenticationRepository
import com.utsman.api.authentication.datasources.AuthenticationNetworkDataSource
import com.utsman.api.authentication.datasources.KeyValueDataSource
import com.utsman.api.authentication.datasources.TokenAuthenticationDataSource
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.datasources.WishlistLocalDataSources
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.apis.product.datasources.ProductPagingSources
import com.utsman.libraries.core.network.TokenAuthentication
import com.utsman.libraries.core.viewmodel.LocalViewModelHost
import com.utsman.libraries.core.viewmodel.ViewModelHost
import com.utsman.libraries.sharedui.customKamelConfig
import io.kamel.image.config.LocalKamelConfig
import kotlinx.coroutines.launch
import screencontainer.DetailScreen
import screencontainer.MainScreen

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }

    val keyValueDataSource = remember { KeyValueDataSource() }
    val tokenAuthentication: TokenAuthentication =
        remember { TokenAuthenticationDataSource(keyValueDataSource) }
    val authenticationNetworkDataSources =
        remember { AuthenticationNetworkDataSource(tokenAuthentication) }
    val authenticationRepository = remember { AuthenticationRepository(
        authenticationNetworkDataSources, keyValueDataSource
    ) }

    val productNetworkDataSources = remember { ProductNetworkDataSource() }
    val wishlistLocalDataSources = remember { WishlistLocalDataSources() }
    val productPagingSources = remember { ProductPagingSources(productNetworkDataSources) }
    val productRepository = remember {
        ProductRepository(
            productNetworkDataSources,
            wishlistLocalDataSources,
            productPagingSources
        )
    }

    CompositionLocalProvider(
        LocalKamelConfig provides customKamelConfig,
        LocalAuthenticationRepository provides authenticationRepository,
        LocalProductRepository provides productRepository,
        LocalViewModelHost provides viewModelHost
    ) {

        ScreenRegistry {
            register<ScreenRoute.MainScreenRoute> {
                MainScreen()
            }
            register<ScreenRoute.DetailScreenRoute> {
                DetailScreen(it.productItemList)
            }
        }

        MaterialTheme {
            Navigator(
                screen = ScreenRegistry.get(ScreenRoute.MainScreenRoute)
            ) {
                VoyagerSwipeable(it)
//                SlideTransition(it)
            }
        }
    }
}

enum class Tab {
    HOME, WISHLIST
}

class TabNavigator {
    var currentTab by mutableStateOf(Tab.HOME)
}

val LocalTabNavigator = compositionLocalOf<TabNavigator> { error("Tab navigator not provided") }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TabItem(tab: Tab, pagerState: PagerState) {
    val tabNavigator = LocalTabNavigator.current

    val isSelected by derivedStateOf { tabNavigator.currentTab == tab }
    val scope = rememberCoroutineScope()

    BottomNavigationItem(
        selected = isSelected,
        onClick = {
            val page = when (tab) {
                Tab.HOME -> 0
                Tab.WISHLIST -> 1
            }
            scope.launch {
                pagerState.animateScrollToPage(page)
            }
            tabNavigator.currentTab = tab
        },
        icon = {},
        label = {
            Text(tab.name.capitalize(Locale.current))
        }
    )
}