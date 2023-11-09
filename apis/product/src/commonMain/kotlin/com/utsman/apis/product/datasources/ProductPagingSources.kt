package com.utsman.apis.product.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.apis.product.model.response.ProductListResponse
import com.utsman.apis.product.model.toProductItemList
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class ProductPagingSources(
    private val networkDataSource: ProductNetworkDataSource
) : PagingSource<Int, ProductItemList>() {
    override fun getRefreshKey(state: PagingState<Int, ProductItemList>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItemList> {
        val page = params.key ?: 1
        return try {
            val dataResponse = networkDataSource.getProduct(page)

            val data = dataResponse
                .body<ProductListResponse>()
                .data?.filterNotNull().orEmpty()
                .map {
                    it.toProductItemList()
                }
            when {
                dataResponse.status.isSuccess() -> {
                    val nextKey = if (data.isNotEmpty()) page + 1 else null
                    val prevKey = (page - 1).takeIf { it >= 1 }
                    PagingSourceLoadResultPage(
                        data = data,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                else -> {
                    val throwable = Throwable(dataResponse.bodyAsText())
                    PagingSourceLoadResultError(throwable)
                }
            }
        } catch (e: Throwable) {
            PagingSourceLoadResultError(
                Throwable("${e.message}")
            )
        }
    }
}