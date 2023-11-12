package com.utsman.features.cart

import com.utsman.apis.cart.model.CartItem
import com.utsman.libraries.core.state.Async

data class CartState(
    val asyncCart: Async<List<CartItem>> = Async.Default
)