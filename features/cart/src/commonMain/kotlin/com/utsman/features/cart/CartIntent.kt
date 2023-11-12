package com.utsman.features.cart

import com.utsman.libraries.core.state.Intent

sealed class CartIntent : Intent {
    data object GetCart : CartIntent()
    data class ToLogin(val toLogin: () -> Unit) : CartIntent()
}