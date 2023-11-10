package com.utsman.features.detail

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.libraries.core.state.Async

data class DetailState(
    val title: String = "",
    val asyncDetail: Async<ProductDetail> = Async.Default
)