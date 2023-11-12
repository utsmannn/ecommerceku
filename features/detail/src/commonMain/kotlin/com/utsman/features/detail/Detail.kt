package com.utsman.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.utils.currency
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.ColorBackgroundItem
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.IconPack
import com.utsman.libraries.sharedui.ImageRemote
import com.utsman.libraries.sharedui.Loading
import com.utsman.libraries.sharedui.ToolbarWithBackNavigation
import com.utsman.libraries.sharedui.iconpack.IcFavoriteFill
import com.utsman.libraries.sharedui.iconpack.IcFavoriteOutline

@Composable
fun Detail(
    productItemList: ProductItemList,
    onBack: () -> Unit
) {

    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel(isRetain = false) { DetailViewModel(productRepository) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(DetailIntent.GetDetail(productItemList.id))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarWithBackNavigation(
                title = uiState.titleBar,
                onBack = onBack
            )
        }
    ) {
        when (val data = uiState.asyncDetail) {
            is Async.Loading -> {
                Loading(modifier = Modifier.fillMaxSize())
            }
            is Async.Failure -> {
                Failure(modifier = Modifier.fillMaxSize(), data.throwable.message)
            }
            is Async.Success -> {
                viewModel.sendIntent(DetailIntent.SetTitleBar(data.data.category.name))
                DetailItem(data.data, viewModel) {
                    viewModel.sendIntent(DetailIntent.ToggleWishlist(productItemList))
                }
            }
            else -> {}
        }
    }
}

@Composable
fun DetailItem(productDetail: ProductDetail, viewModel: DetailViewModel, onWishlistClicked: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            ImageRemote(
                url = productDetail.images.first(),
                modifier = Modifier.fillMaxWidth()
                    .height(220.dp)
                    .background(ColorBackgroundItem)
            )
        }
        item {
            Column(
                modifier = Modifier.padding(6.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .padding(end = 12.dp)
                    ) {
                        Text(
                            text = productDetail.name,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = productDetail.price.currency,
                            fontWeight = FontWeight.Black
                        )
                    }

                    val iconResources = if (uiState.isWishlistExists) {
                        IconPack.IcFavoriteFill
                    } else {
                        IconPack.IcFavoriteOutline
                    }

                    IconButton(
                        onClick = {
                            onWishlistClicked.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = iconResources,
                            contentDescription = null
                        )
                    }
                }

                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp))

                SelectionContainer {
                    Column {
                        Text(
                            text = productDetail.description
                        )
                        Text(
                            text = productDetail.description
                        )
                    }
                }
            }
        }
    }
}