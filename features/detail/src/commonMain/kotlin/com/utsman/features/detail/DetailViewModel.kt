package com.utsman.features.detail

import com.utsman.apis.product.ProductRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val productRepository: ProductRepository
) : ViewModel<DetailState, DetailIntent>(DetailState()) {

    fun getProductDetail(productId: Int) = viewModelScope.launch {
        productRepository.getProductDetail(productId)
            .stateIn(viewModelScope)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncDetail = it
                    )
                }
            }
    }
}