package com.utsman.apis.cart

import androidx.compose.runtime.compositionLocalOf
import com.utsman.apis.cart.datasources.CartNetworkDataSource
import com.utsman.apis.cart.model.CartItem
import com.utsman.apis.cart.model.CartResponse
import com.utsman.apis.cart.model.toCartItem
import com.utsman.apis.product.ProductRepository
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(
    private val cartNetworkDataSource: CartNetworkDataSource,
    private val productRepository: ProductRepository
) : Repository() {

    suspend fun getCart(): Flow<Async<List<CartItem>>> {
        return suspend {
            cartNetworkDataSource.getCart()
        }.reduceSuspend<CartResponse, List<CartItem>> { response ->
            if (response.status == true) {
                val responseData = response.data?.filterNotNull().orEmpty()
                val data = responseData.map { responseItem ->
                    val asyncProductFlow = productRepository.getProductDetail(responseItem.productId ?: 0)
                        .map {
                            when (it) {
                                is Async.Default -> Async.Default
                                is Async.Loading -> Async.Loading
                                is Async.Success -> Async.Success(it.data)
                                else -> Async.Failure(Throwable(""))
                            }
                        }
                    responseItem.toCartItem(asyncProductFlow)
                }
                Async.Success(data)
            } else {
                Async.Failure(Throwable(response.message))
            }
        }
    }
}

val LocalCartRepository = compositionLocalOf<CartRepository> { error("cart repository not provided!") }