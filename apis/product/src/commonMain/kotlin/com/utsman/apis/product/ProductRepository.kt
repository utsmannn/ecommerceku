package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

class ProductRepository {

    val count: MutableStateFlow<Int> = MutableStateFlow(0)
}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("not provided!") }