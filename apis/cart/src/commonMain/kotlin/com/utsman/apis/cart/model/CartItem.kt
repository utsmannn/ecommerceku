package com.utsman.apis.cart.model

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

data class CartItem(
    val productId: Int,
    val asyncProductFlow: Flow<Async<ProductDetail>>,
    val discount: Int,
    val amount: Double,
    val quantity: Int
)