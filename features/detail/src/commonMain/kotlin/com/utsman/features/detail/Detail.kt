package com.utsman.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.utsman.apis.product.LocalProductRepository
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.utils.currency
import com.utsman.libraries.core.viewmodel.rememberViewModel
import com.utsman.libraries.sharedui.ColorBackgroundItem
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.ImageRemote
import com.utsman.libraries.sharedui.Loading
import com.utsman.libraries.sharedui.ToolbarWithBackNavigation

@Composable
fun Detail(
    productId: Int,
    onBack: () -> Unit
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolbarWithBackNavigation(
                title = uiState.title,
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
                DetailItem(data.data)
            }
            else -> {}
        }
    }
}

@Composable
fun DetailItem(productDetail: ProductDetail) {
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

                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp))

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