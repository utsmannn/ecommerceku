package com.utsman.features.home.viewmodel

import com.utsman.apis.product.ProductRepository
import com.utsman.libraries.core.viewmodel.ViewModel

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {
    var count = productRepository.count
}