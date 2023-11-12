import cafe.adriel.voyager.core.registry.ScreenProvider
import com.utsman.apis.product.model.entity.ProductItemList

sealed class ScreenRoute : ScreenProvider {
    data object MainScreenRoute : ScreenRoute()
    data class DetailScreenRoute(val productItemList: ProductItemList) : ScreenRoute()
}