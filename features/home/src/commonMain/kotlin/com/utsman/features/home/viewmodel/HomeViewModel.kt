package com.utsman.features.home.viewmodel

import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.model.ProductItemList
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _productList: MutableStateFlow<List<ProductItemList>> = MutableStateFlow(emptyList())
    val productList: StateFlow<List<ProductItemList>> = _productList

    fun getProductList(page: Int) = viewModelScope.launch {
        productRepository.getProductList(page)
            .stateIn(this)
            .collect(_productList)
    }
}