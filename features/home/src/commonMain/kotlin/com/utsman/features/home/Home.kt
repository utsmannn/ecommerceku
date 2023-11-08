package com.utsman.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
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
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.Loading
import com.utsman.libraries.sharedui.ProductItem

@Composable
fun Home() {
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { HomeViewModel(productRepository) }
    val uiState by viewModel.homeUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProductList(1)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp)
    ) {

        when (val async = uiState.asyncProduct) {
            is Async.Loading -> {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Loading(modifier = Modifier.fillMaxWidth())
                }
            }
            is Async.Failure -> {
                val throwable = async.throwable
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Failure(modifier = Modifier.fillMaxWidth(), throwable.message)
                }
            }
            is Async.Success -> {
                val data = async.data
                items(data) { product ->
                    ProductItem(product)
                }
            }
            else -> {}
        }
    }
}