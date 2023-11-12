package screencontainer

import ScreenRoute
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.utsman.features.cart.Cart
import com.utsman.features.home.ui.Home
import com.utsman.features.wishlist.Wishlist
import kotlinx.coroutines.launch
import uuid

enum class Tab {
    HOME, WISHLIST, CART
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
                Tab.CART -> 2
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


class HomePagerScreen : Screen {

    override val key: ScreenKey
        get() = uuid()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val tabNavigator = remember { TabNavigator() }

        CompositionLocalProvider(
            LocalTabNavigator provides tabNavigator
        ) {
            val navigator = LocalNavigator.currentOrThrow

            val pagerState = rememberPagerState { 3 }

            when (pagerState.currentPage) {
                0 -> {
                    tabNavigator.currentTab = Tab.HOME
                }
                1 -> {
                    tabNavigator.currentTab = Tab.WISHLIST
                }
                2 -> {
                    tabNavigator.currentTab = Tab.CART
                }
            }

            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        TabItem(Tab.HOME, pagerState)
                        TabItem(Tab.WISHLIST, pagerState)
                        TabItem(Tab.CART, pagerState)
                    }
                }
            ) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(bottom = 56.dp),
                    beyondBoundsPageCount = 3
                ) { index ->
                    when (index) {
                        0 -> {
                            Home {
                                navigator.push(ScreenRegistry.get(ScreenRoute.DetailScreenRoute(it)))
                            }
                        }
                        1 -> {
                            Wishlist {
                                navigator.push(ScreenRegistry.get(ScreenRoute.DetailScreenRoute(it)))
                            }
                        }
                        2 -> {
                            Cart {
                                mainScreenStack.push(LoginScreen())
                            }
                        }
                    }
                }
            }
        }
    }

}