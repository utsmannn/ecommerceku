package com.utsman.features.detail

import com.utsman.apis.product.model.ProductDetail
import com.utsman.libraries.core.state.Async

data class DetailState(
    val asyncDetail: Async<ProductDetail> = Async.Default
)