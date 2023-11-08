package com.utsman.features.home.state

import com.utsman.libraries.core.state.Intent

sealed class HomeIntent : Intent {
    data class LoadProductList(val page: Int) : HomeIntent()
    data class ShowSnackBar(val message: String) : HomeIntent()
}