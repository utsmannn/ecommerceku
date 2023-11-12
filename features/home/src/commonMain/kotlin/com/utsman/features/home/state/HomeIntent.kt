package com.utsman.features.home.state

import androidx.compose.material.ScaffoldState
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope

sealed class HomeIntent : Intent {
    data class ShowSnackBar(val message: String, val scaffoldState: ScaffoldState, val scope: CoroutineScope) : HomeIntent()
    data class ToDetail(val onClickItem: (ProductItemList) -> Unit, val productItemList: ProductItemList) : HomeIntent()
    data class SavePosition(val position: Int) : HomeIntent()
}