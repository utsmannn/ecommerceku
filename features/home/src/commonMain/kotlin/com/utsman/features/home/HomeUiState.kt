package com.utsman.features.home

import com.utsman.apis.product.model.ProductItemList

data class HomeUiState(
    val isLoading: Boolean = true,
    val productList: List<ProductItemList> = emptyList()
)