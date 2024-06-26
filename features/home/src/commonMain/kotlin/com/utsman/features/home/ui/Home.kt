package com.utsman.features.home.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.features.home.state.HomeIntent
import com.utsman.features.home.viewmodel.HomeViewModel
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.Loading
import com.utsman.libraries.sharedui.ProductItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

@Composable
fun Home(onClickItem: (ProductItemList) -> Unit) {
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { HomeViewModel(productRepository) }
    val uiState by viewModel.uiState.collectAsState()

    val productPaged = viewModel.productPagedFlow.collectAsLazyPagingItems()

    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
        ) {

            items(productPaged.itemCount) { index ->
                val item = productPaged[index]
                item?.let { product ->
                    ProductItem(product) {
                        viewModel.sendIntent(
                            HomeIntent.ToDetail(
                                onClickItem, it
                            )
                        )
                    }
                }
            }

            when {
                productPaged.loadState.refresh is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Loading(modifier = Modifier.fillMaxWidth())
                    }
                }
                productPaged.loadState.refresh is LoadState.Error -> {
                    val loadState = productPaged.loadState.append as LoadState.Error
                    val throwable = loadState.error
                    item(span = { GridItemSpan(2) }) {
                        Failure(modifier = Modifier.fillMaxWidth(), throwable.message)
                    }
                }
                productPaged.loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Loading(modifier = Modifier.fillMaxWidth())
                    }
                }
                productPaged.loadState.append is LoadState.Error -> {
                    val loadState = productPaged.loadState.append as LoadState.Error
                    val throwable = loadState.error
                    item(span = { GridItemSpan(2) }) {
                        Failure(modifier = Modifier.fillMaxWidth(), throwable.message)
                    }
                }
            }
        }
    }
}