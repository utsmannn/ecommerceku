package com.utsman.features.home.state

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.paging.PagingData
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.libraries.core.state.Async

data class HomeUiState(
    val asyncProduct: Async<List<ProductItemList>> = Async.Default,
    val scrollPosition: Int = 0
)