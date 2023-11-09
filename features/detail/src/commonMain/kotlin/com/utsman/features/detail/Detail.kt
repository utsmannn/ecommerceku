package com.utsman.features.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Detail(productId: Int) {
    Text(productId.toString())
}