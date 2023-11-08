package com.utsman.features.home.viewmodel

import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.model.ProductItemList
import com.utsman.features.home.HomeUiState
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _productList: MutableStateFlow<List<ProductItemList>> = MutableStateFlow(emptyList())
    val productList: StateFlow<List<ProductItemList>> = _productList

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    fun getProductList(page: Int) = viewModelScope.launch {
        productRepository.getProductList(page)
            .stateIn(this)
            .onStart {
                updateUiState {
                    copy(
                        isLoading = true
                    )
                }
            }
            .collectLatest {
                updateUiState {
                    copy(
                        isLoading = false,
                        productList = it
                    )
                }
            }
    }

    private fun updateUiState(block: HomeUiState.() -> HomeUiState) {
        val currentUiState = _homeUiState.value
        val newUiState = block.invoke(currentUiState)
        _homeUiState.value = newUiState
    }
}