package com.utsman.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.utsman.libraries.core.viewmodel.ViewModel
import com.utsman.libraries.core.viewmodel.rememberViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    var count = MutableStateFlow(0)
}

@Composable
fun Home() {
    Column {
        val viewModel = rememberViewModel { HomeViewModel() }
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