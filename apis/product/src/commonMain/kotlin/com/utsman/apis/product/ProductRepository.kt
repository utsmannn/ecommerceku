package com.utsman.apis.product

import kotlinx.coroutines.flow.MutableStateFlow

class ProductRepository {

    val count: MutableStateFlow<Int> = MutableStateFlow(0)
}