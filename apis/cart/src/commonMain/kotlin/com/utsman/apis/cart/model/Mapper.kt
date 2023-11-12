package com.utsman.apis.cart.model

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

fun CartResponse.DataResponse.toCartItem(
    asyncProductFlow: Flow<Async<ProductDetail>>
): CartItem {
    return CartItem(
        productId = productId ?: 0,
        asyncProductFlow = asyncProductFlow,
        discount = discount ?: 0,
        amount = amount ?: 0.0,
        quantity = quantity ?: 0
    )
}