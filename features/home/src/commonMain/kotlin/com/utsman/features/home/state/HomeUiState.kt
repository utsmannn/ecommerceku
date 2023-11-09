package com.utsman.features.home.state

import androidx.paging.PagingData
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Async

data class HomeUiState(
    val asyncProduct: Async<List<ProductItemList>> = Async.Default
)