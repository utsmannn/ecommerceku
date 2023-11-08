package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.utsman.apis.product.model.ProductItemList
import com.utsman.apis.product.model.ProductListResponse
import com.utsman.apis.product.model.toProductItemList
import com.utsman.libraries.core.network.NetworkDataSources
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProductRepository(
    private val networkDataSources: NetworkDataSources
) {
    suspend fun getProductList(page: Int): Flow<List<ProductItemList>> {
        return flow {
            try {
                val httpResponse = networkDataSources.getHttpResponse("/product?page=$page")
                if (!httpResponse.status.isSuccess()) {
                    throw Throwable(httpResponse.bodyAsText())
                }

                val dataResponse = httpResponse.body<ProductListResponse>()
                val dataList = dataResponse.data?.filterNotNull()?.map { it.toProductItemList() }.orEmpty()
                emit(dataList)
            } catch (e: IOException) {
                throw Throwable("Internet offline")
            }
        }
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("not provided!") }