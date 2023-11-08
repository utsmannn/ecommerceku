package com.utsman.features.home

import com.utsman.apis.product.model.ProductItemList
import com.utsman.libraries.core.state.Async

data class HomeUiState(
    val asyncProduct: Async<List<ProductItemList>> = Async.Default
)