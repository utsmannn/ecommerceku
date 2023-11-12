package com.utsman.features.cart

import com.utsman.apis.cart.CartRepository
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel<CartState, CartIntent>(CartState())  {
    override fun sendIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.GetCart -> {
                getCart()
            }
            is CartIntent.ToLogin -> {
                intent.toLogin.invoke()
            }
        }
    }

    private fun getCart() = viewModelScope.launch {
        cartRepository.getCart()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncCart = it
                    )
                }
            }
    }
}