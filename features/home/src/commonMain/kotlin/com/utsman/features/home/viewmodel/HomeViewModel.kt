package com.utsman.features.home.viewmodel

import androidx.paging.cachedIn
import com.utsman.apis.product.ProductRepository
import com.utsman.features.home.state.HomeIntent
import com.utsman.features.home.state.HomeUiState
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel<HomeUiState, HomeIntent>(HomeUiState()) {

    val productPagedFlow = productRepository.productPagingFlow.cachedIn(viewModelScope)

    fun getProductList(page: Int) = viewModelScope.launch {
        productRepository.getProductList(page)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProduct = it)
                }
            }
    }
}