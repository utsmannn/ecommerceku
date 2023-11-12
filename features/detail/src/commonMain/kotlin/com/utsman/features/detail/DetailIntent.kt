package com.utsman.features.detail

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Intent

sealed class DetailIntent : Intent {
    data class GetDetail(val productId: Int) : DetailIntent()
    data class ToggleWishlist(val productItemList: ProductItemList) : DetailIntent()
    data class SetTitleBar(val titleBar: String) : DetailIntent()
}