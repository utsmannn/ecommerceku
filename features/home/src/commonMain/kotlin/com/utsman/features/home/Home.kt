package com.utsman.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utsman.apis.product.LocalProductRepository
import com.utsman.features.home.viewmodel.HomeViewModel
import com.utsman.libraries.core.viewmodel.rememberViewModel

@Composable
fun Home() {
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { HomeViewModel(productRepository) }
    val productList by viewModel.productList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProductList(1)
    }

    LazyColumn {
        items(productList) { product ->
           Text(
               text = product.name,
               modifier = Modifier.padding(12.dp)
           )
        }
    }
}