package com.utsman.features.wishlist

import com.utsman.apis.product.ProductRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val productRepository: ProductRepository
) : ViewModel<WishlistState, WishlistIntent>(WishlistState()) {

    override fun sendIntent(intent: WishlistIntent) {
        when (intent) {
            is WishlistIntent.GetWishlist -> {
                getWishlist()
            }
        }
    }

    private fun getWishlist() = viewModelScope.launch {
        productRepository.getWishlistAll()
            .onEach {
                updateUiState {
                    copy(
                        asyncWishlist = it
                    )
                }
            }
            .collect()
    }
}