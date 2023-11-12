package com.utsman.features.home.viewmodel

import androidx.paging.cachedIn
import com.utsman.apis.product.ProductRepository
import com.utsman.features.home.state.HomeIntent
import com.utsman.features.home.state.HomeUiState
import com.utsman.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository
) : ViewModel<HomeUiState, HomeIntent>(HomeUiState()) {

    val productPagedFlow = productRepository
        .productPager
        .flow
        .cachedIn(viewModelScope)

    override fun sendIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ShowSnackBar -> {
                val message = intent.message
                intent.scope.launch {
                    intent.scaffoldState.snackbarHostState.showSnackbar(message)
                }
            }
            is HomeIntent.ToDetail -> {
                intent.onClickItem.invoke(intent.productItemList)
            }
            is HomeIntent.SavePosition -> {
                updateUiState {
                    copy(
                        scrollPosition = intent.position
                    )
                }
            }
        }
    }
}