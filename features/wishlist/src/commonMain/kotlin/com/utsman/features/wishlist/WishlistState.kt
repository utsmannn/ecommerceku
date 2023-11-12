package com.utsman.features.wishlist

import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Async

data class WishlistState(
    val asyncWishlist: Async<List<ProductItemList>> = Async.Default,
    val isWishlistExist: Boolean = false
)