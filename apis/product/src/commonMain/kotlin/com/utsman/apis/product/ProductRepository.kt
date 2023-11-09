package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.apis.product.model.ProductDetail
import com.utsman.apis.product.model.ProductDetailResponse
import com.utsman.apis.product.model.ProductItemList
import com.utsman.apis.product.model.ProductListResponse
import com.utsman.apis.product.model.toProductDetail
import com.utsman.apis.product.model.toProductItemList
import com.utsman.libraries.core.network.NetworkDataSources
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow


class ProductRepository(
    private val networkDataSources: ProductNetworkDataSource
) : Repository() {

    suspend fun getProductList(page: Int): Flow<Async<List<ProductItemList>>> {
        return suspend {
            networkDataSources.getProduct(page)
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

    suspend fun getProductDetail(productId: Int): Flow<Async<ProductDetail>> {
        return suspend {
            networkDataSources.getProductId(productId)
        }.reduce<ProductDetailResponse, ProductDetail> { response ->
            val responseData = response.data

            if (responseData == null) {
                val emptyThrowable = Throwable("Product is empty")
                Async.Failure(emptyThrowable)
            } else{
                val data = responseData.toProductDetail()
                Async.Success(data)
            }
        }
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("not provided!") }