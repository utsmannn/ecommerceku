package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.utsman.apis.product.model.ProductItemList
import com.utsman.apis.product.model.ProductListResponse
import com.utsman.apis.product.model.toProductItemList
import com.utsman.libraries.core.network.NetworkDataSources
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow


class ProductRepository(
    private val networkDataSources: NetworkDataSources
) : Repository() {

    suspend fun getProductList(page: Int): Flow<Async<List<ProductItemList>>> {
        return suspend {
            networkDataSources.getHttpResponse("/product?page=$page")
        }.reduce<ProductListResponse, List<ProductItemList>> { response ->
            val responseList = response.data?.filterNotNull().orEmpty()
            if (responseList.isEmpty()) {
                val emptyThrowable = Throwable("Product is empty")
                Async.Failure(emptyThrowable)
            } else {
                val dataList = responseList.map {
                    it.toProductItemList()
                }
                Async.Success(dataList)
            }
        }
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("not provided!") }