package com.utsman.features.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import com.utsman.apis.product.ProductRepository
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.viewmodel.ViewModel
import com.utsman.libraries.sharedui.IconPack
import com.utsman.libraries.sharedui.iconpack.IcFavoriteFill
import com.utsman.libraries.sharedui.iconpack.IcFavoriteOutline
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val productRepository: ProductRepository
) : ViewModel<DetailState, DetailIntent>(DetailState()) {

    override fun sendIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.GetDetail -> {
                getProductDetail(intent.productId)
                checkIsWishlist(intent.productId)
            }
            is DetailIntent.ToggleWishlist -> {
                toggleWishlist(intent.productItemList)
            }
            is DetailIntent.SetTitleBar -> {
                updateUiState {
                    copy(
                        titleBar = intent.titleBar
                    )
                }
            }
        }
    }

    private fun getProductDetail(productId: Int) = viewModelScope.launch {
        productRepository.getProductDetail(productId)
            .stateIn(viewModelScope)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncDetail = it
                    )
                }
            }
    }

    private fun checkIsWishlist(productId: Int) = viewModelScope.launch {
        productRepository.isWishlistExist(productId)
            .stateIn(viewModelScope)
            .collectLatest {
                updateUiState {
                    copy(
                        isWishlistExists = it,
                        iconWishlist = if (it) {
                            IconPack.IcFavoriteFill
                        } else {
                            IconPack.IcFavoriteOutline
                        }
                    )
                }
            }
    }

    private fun toggleWishlist(productItemList: ProductItemList) = viewModelScope.launch {
        if (uiState.value.isWishlistExists) {
            productRepository.deleteWishlist(productItemList.id)
        } else {
            productRepository.addToWishlist(productItemList)
        }
    }
}