package com.utsman.apis.product.model

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.apis.product.model.response.ProductDetailResponse
import com.utsman.apis.product.model.response.ProductListResponse

fun ProductListResponse.DataResponse.toProductItemList(): ProductItemList {
    return ProductItemList(
        id = id ?: 0,
        name = name ?: "",
        sortDescription = sortDescription ?: "",
        category = category?.toCategoryItem() ?: ProductItemList.CategoryItem(0, "", ""),
        price = price ?: 0.0,
        rating = rating ?: 0.0,
        discount = discount ?: 0,
        images = images ?: ""
    )
}

fun ProductListResponse.DataResponse.CategoryResponse.toCategoryItem(): ProductItemList.CategoryItem {
    return ProductItemList.CategoryItem(
        id = id ?: 0,
        name = name ?: "",
        description = description ?: ""
    )
}

fun ProductDetailResponse.DataResponse.toProductDetail(): ProductDetail {
    return ProductDetail(
        id = id ?: 0,
        name = name.orEmpty(),
        description = description.orEmpty(),
        category = category?.toCategoryDetail() ?: ProductDetail.ProductCategory(0, "", ""),
        rating = rating ?: 0.0,
        price = price ?: 0.0,
        discount = discount ?: 0,
        images = images.orEmpty().filterNotNull(),
        userReview = userReview?.mapNotNull {
            it?.toUserReviewDetail()
        }.orEmpty()
    )
}

fun ProductDetailResponse.DataResponse.UserReviewResponse.toUserReviewDetail(): ProductDetail.ProductUserReview {
    return ProductDetail.ProductUserReview(
        user = user.orEmpty(),
        review = review.orEmpty()
    )
}

fun ProductDetailResponse.DataResponse.CategoryResponse.toCategoryDetail(): ProductDetail.ProductCategory {
    return ProductDetail.ProductCategory(
        id = id ?: 0,
        name = name.orEmpty(),
        description = description.orEmpty()
    )
}