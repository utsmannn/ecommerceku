package com.utsman.apis.product.datasources

import com.utsman.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay

class ProductNetworkDataSource : NetworkDataSources(BASE_URL) {

    suspend fun getProduct(page: Int = 1): HttpResponse {
        val endPoint = "$PRODUCT_ENDPOINT?page=$page"
        delay(2000)
        return getHttpResponse(endPoint)
    }

    suspend fun getProductId(id: Int): HttpResponse {
        val endPoint = "$PRODUCT_ENDPOINT/$id"
        delay(2000)
        return getHttpResponse(endPoint)
    }

    companion object {
        private const val BASE_URL = "https://marketfake.fly.dev"
        private const val PRODUCT_ENDPOINT = "/product"
    }
}