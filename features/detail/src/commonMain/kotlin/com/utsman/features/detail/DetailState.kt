package com.utsman.features.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.libraries.core.state.Async

data class DetailState(
    val titleBar: String = "",
    val asyncDetail: Async<ProductDetail> = Async.Default,
    val isWishlistExists: Boolean = false,
    val iconWishlist: ImageVector = Icons.Outlined.Star
)