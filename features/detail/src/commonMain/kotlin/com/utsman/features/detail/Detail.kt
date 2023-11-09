package com.utsman.features.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.model.ProductDetail
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.Loading

@Composable
fun Detail(
    productId: Int
) {

    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel(isRetain = false) { DetailViewModel(productRepository) }
    val uiState by viewModel.uiState.collectAsState()
    val intent by viewModel.intent.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(DetailIntent.GetDetail(productId))
    }

    LaunchedEffect(intent) {
        when (val data = intent) {
            is DetailIntent.GetDetail -> {
                viewModel.getProductDetail(data.productId)
            }
        }
    }

    when (val data = uiState.asyncDetail) {
        is Async.Loading -> {
            Loading(modifier = Modifier.fillMaxSize())
        }
        is Async.Failure -> {
            Failure(modifier = Modifier.fillMaxSize(), data.throwable.message)
        }
        is Async.Success -> {
            DetailItem(data.data)
        }
        else -> {}
    }
}

@Composable
fun DetailItem(productDetail: ProductDetail) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(productDetail.name)
    }
}