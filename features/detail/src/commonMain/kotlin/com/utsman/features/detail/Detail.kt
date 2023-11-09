package com.utsman.features.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Detail(productId: Int) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(productId.toString())
    }
}