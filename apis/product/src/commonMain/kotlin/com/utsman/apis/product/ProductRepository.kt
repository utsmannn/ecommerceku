package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.apis.product.datasources.ProductPagingSources
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.response.ProductDetailResponse
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.apis.product.model.response.ProductListResponse
import com.utsman.apis.product.model.toProductDetail
import com.utsman.apis.product.model.toProductItemList
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow


class ProductRepository(
    private val networkDataSources: ProductNetworkDataSource,
    private val productPagingSources: ProductPagingSources
) : Repository() {

    val productPagingFlow = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        productPagingSources
    }.flow

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