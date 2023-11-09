package com.utsman.features.home.viewmodel

import com.utsman.apis.product.ProductRepository
import com.utsman.features.home.state.HomeIntent
import com.utsman.features.home.state.HomeUiState
import com.utsman.libraries.core.state.Intent
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _homeIntent: MutableStateFlow<Intent> = MutableStateFlow(Intent.Idle)
    val homeIntent: MutableStateFlow<Intent> = _homeIntent

    fun sendIntent(homeIntent: HomeIntent) = viewModelScope.launch {
        _homeIntent.value = homeIntent
        delay(80)
        _homeIntent.value = Intent.Idle
    }

    fun getProductList(page: Int) = viewModelScope.launch {
        productRepository.getProductList(page)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProduct = it)
                }
            }
    }

    private fun updateUiState(block: HomeUiState.() -> HomeUiState) {
        val currentUiState = _homeUiState.value
        val newUiState = block.invoke(currentUiState)
        _homeUiState.value = newUiState
    }
}