package com.utsman.apis.product

import androidx.compose.runtime.compositionLocalOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.utsman.apis.product.datasources.WishlistLocalDataSources
import com.utsman.apis.product.datasources.ProductNetworkDataSource
import com.utsman.apis.product.datasources.ProductPagingSources
import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.response.ProductDetailResponse
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.apis.product.model.response.ProductListResponse
import com.utsman.apis.product.model.toProductDetail
import com.utsman.apis.product.model.toProductItem
import com.utsman.apis.product.model.toProductItemList
import com.utsman.apis.product.model.toWishlist
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext


class ProductRepository(
    private val productNetworkDataSources: ProductNetworkDataSource,
    private val localDataSources: WishlistLocalDataSources,
    private val pagingSources: ProductPagingSources
) : Repository() {

    val productPager = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        pagingSources
    }

    suspend fun getProductDetail(productId: Int): Flow<Async<ProductDetail>> {
        return suspend {
            productNetworkDataSources.getProductId(productId)
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

    suspend fun getWishlistAll(): Flow<Async<List<ProductItemList>>> {
        return withContext(Dispatchers.IO) {
            flow {
                localDataSources
                    .getAllItem()
                    .map { list ->
                        list.map { it.toProductItem() }
                    }
                    .onEach {
                        emit(Async.Success(it))
                    }
                    .catch {
                        emit(Async.Failure(it))
                    }
                    .onStart {
                        emit(Async.Loading)
                    }
                    .collect()
            }
        }
    }

    suspend fun addToWishlist(productItemList: ProductItemList) {
        localDataSources.insertItem(productItemList.toWishlist())
    }

    suspend fun deleteWishlist(id: Int) {
        localDataSources.deleteItem(id)
    }

    suspend fun isWishlistExist(id: Int): Flow<Boolean> {
        return localDataSources.isItemExist(id)
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("not provided!") }