package com.utsman.features.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.utsman.apis.cart.LocalCartRepository
import com.utsman.apis.cart.model.CartItem
import com.utsman.libraries.core.network.UnauthorizedException
import com.utsman.libraries.core.state.Async
import com.utsman.libraries.core.utils.currency
import com.utsman.libraries.sharedui.Failure
import com.utsman.libraries.sharedui.ImageRemote
import com.utsman.libraries.sharedui.Loading

@Composable
fun Cart(toLogin: () -> Unit) {
    val cartRepository = LocalCartRepository.current
    val viewModel = remember { CartViewModel(cartRepository) }
    val state by viewModel.uiState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CartIntent.GetCart)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {

        when (val async = state.asyncCart) {
            is Async.Loading -> {
                Loading(modifier = Modifier.fillMaxSize())
            }
            is Async.Failure -> {
                if (async.throwable is UnauthorizedException) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                viewModel.sendIntent(CartIntent.ToLogin(toLogin))
                            }
                        ) {
                            Text("Login")
                        }
                    }

                } else {
                    Failure(
                        modifier = Modifier.fillMaxSize(),
                        text = async.throwable.message
                    )
                }
            }
            is Async.Success -> {
                CartSuccess(async.data)
            }
            else -> {}
        }

    }
}

@Composable
fun CartSuccess(carts: List<CartItem>) {
    LazyColumn {

        if (carts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("cart is empty")
                }
            }
        }

        items(carts) { cartItem ->
            Box(
                modifier = Modifier.height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                ItemCart(cartItem)
            }
        }
    }
}

@Composable
fun ItemCart(cartItem: CartItem) {
    val asyncProduct by cartItem.asyncProductFlow.collectAsState(Async.Default)
    when (val async = asyncProduct) {
        is Async.Loading -> {
            Loading()
        }
        is Async.Failure -> {
            Failure(text = async.throwable.message)
        }
        is Async.Success -> {
            Row {
                ImageRemote(
                    url = async.data.images.first(),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1/3f)
                        .padding(vertical = 12.dp)
                        .padding(start = 12.dp, end = 6.dp)
                )

                Column(
                    modifier = Modifier.weight(2/3f)
                        .padding(vertical = 12.dp)
                        .padding(start = 6.dp, end = 12.dp)
                ) {
                    Text(
                        text = async.data.name,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${cartItem.quantity} x ${async.data.price.currency}"
                    )
                }
            }
        }
        else -> {}
    }
}
