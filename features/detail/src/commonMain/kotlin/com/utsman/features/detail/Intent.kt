package com.utsman.features.detail

import com.utsman.libraries.core.state.Intent

sealed class DetailIntent : Intent {
    data class GetDetail(val productId: Int) : DetailIntent()
}