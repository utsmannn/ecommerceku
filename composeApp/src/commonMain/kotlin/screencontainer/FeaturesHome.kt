package screencontainer

import LocalTabNavigator
import ScreenRoute
import Tab
import TabItem
import TabNavigator
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.utsman.features.home.ui.Home
import com.utsman.features.wishlist.Wishlist
import uuid


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

            val pagerState = rememberPagerState { 2 }

            when (pagerState.currentPage) {
                0 -> {
                    tabNavigator.currentTab = Tab.HOME
                }
                1 -> {
                    tabNavigator.currentTab = Tab.WISHLIST
                }
            }

            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        TabItem(Tab.HOME, pagerState)
                        TabItem(Tab.WISHLIST, pagerState)
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
                    }
                }
            }
        }
    }

}