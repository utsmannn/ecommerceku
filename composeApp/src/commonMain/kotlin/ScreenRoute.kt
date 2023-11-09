import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class ScreenRoute : ScreenProvider {
    data object Home : ScreenRoute()
    data class Detail(val productId: Int) : ScreenRoute()
}