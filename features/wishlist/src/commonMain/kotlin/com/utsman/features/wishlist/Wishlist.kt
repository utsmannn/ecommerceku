package com.utsman.features.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.Loading
import com.utsman.libraries.sharedui.ProductItem


@Composable
fun Wishlist(onClick: (ProductItemList) -> Unit) {
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { WishlistViewModel(productRepository) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(WishlistIntent.GetWishlist)
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp)
    ) {

        when (val async = uiState.asyncWishlist) {
            is Async.Loading -> {
                item(span = { GridItemSpan(2) }) {
                    Loading(modifier = Modifier.fillMaxWidth())
                }
            }
            is Async.Failure -> {
                item(span = { GridItemSpan(2) }) {
                    Failure(
                        modifier = Modifier.fillMaxWidth(),
                        text = async.throwable.message
                    )
                }
            }
            is Async.Success -> {
                val data = async.data
                if (data.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Wishlist empty")
                        }
                    }
                }

                items(data) {
                    ProductItem(it) { wishlist ->
                        onClick.invoke(wishlist)
                    }
                }
            }
            else -> {}
        }

    }
}