package screencontainer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.features.detail.Detail
import uuid

data class DetailScreen(
    val productItemList: ProductItemList
) : Screen {

    override val key: ScreenKey
        get() = uuid()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Detail(productItemList) {
            navigator.pop()
        }
    }

}