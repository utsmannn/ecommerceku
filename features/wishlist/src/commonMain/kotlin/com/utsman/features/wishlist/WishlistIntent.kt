package com.utsman.features.wishlist

import com.utsman.libraries.core.state.Intent

sealed class WishlistIntent : Intent {
    data object GetWishlist : WishlistIntent()
}