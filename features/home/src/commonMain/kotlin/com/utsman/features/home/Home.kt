package com.utsman.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.utsman.apis.product.ProductRepository
import com.utsman.features.home.viewmodel.HomeViewModel
import com.utsman.libraries.core.viewmodel.rememberViewModel

@Composable
fun Home() {
    Column {
        val viewModel = rememberViewModel { HomeViewModel(ProductRepository()) }
        val count by viewModel.count.collectAsState()
        Text("Count: $count")

        Button(
            onClick = {
                viewModel.count.value = count + 1
            }
        ) {
            Text("hitung")
        }
    }
}