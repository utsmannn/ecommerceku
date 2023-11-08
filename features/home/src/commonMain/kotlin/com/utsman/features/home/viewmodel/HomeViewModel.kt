package com.utsman.features.home.viewmodel

import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.model.ProductItemList
import com.utsman.features.home.HomeUiState
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
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
            .onStart {
                updateUiState {
                    copy(
                        asyncProduct = Async.Loading
                    )
                }
            }
            .catch {
                it.printStackTrace()
                updateUiState {
                    copy(
                        asyncProduct = Async.Failure(it)
                    )
                }
            }
            .onEach {
                updateUiState {
                    copy(
                        asyncProduct = Async.Success(it)
                    )
                }
            }
            .stateIn(this)
            .collect()
    }

    private fun updateUiState(block: HomeUiState.() -> HomeUiState) {
        val currentUiState = _homeUiState.value
        val newUiState = block.invoke(currentUiState)
        _homeUiState.value = newUiState
    }
}