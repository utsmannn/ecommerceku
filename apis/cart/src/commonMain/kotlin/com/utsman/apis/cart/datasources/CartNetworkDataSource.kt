package com.utsman.apis.cart.datasources

import com.utsman.libraries.core.network.NetworkDataSources
import com.utsman.libraries.core.network.TokenAuthentication
import io.ktor.client.statement.HttpResponse

class CartNetworkDataSource(
    tokenAuthentication: TokenAuthentication
) : NetworkDataSources(BASE_URL, tokenAuthentication) {

    suspend fun getCart(): HttpResponse {
        return getHttpResponse(CART_ENDPOINT)
    }

    companion object {
        private const val BASE_URL = "https://marketfake.fly.dev"
        private const val CART_ENDPOINT = "/cart"
    }
}