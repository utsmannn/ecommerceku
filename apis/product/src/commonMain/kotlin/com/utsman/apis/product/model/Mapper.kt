package com.utsman.apis.product.model

import com.utsman.apis.product.model.entity.ProductDetail
import com.utsman.apis.product.model.entity.ProductItemList
import com.utsman.apis.product.model.local.CategoryItemRealm
import com.utsman.apis.product.model.local.WishlistItemRealm
import com.utsman.apis.product.model.response.ProductDetailResponse
import com.utsman.apis.product.model.response.ProductListResponse

fun ProductListResponse.DataResponse.toProductItemList(): ProductItemList {
    return ProductItemList(
        id = id ?: 0,
        name = name ?: "",
        shortDescription = sortDescription ?: "",
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

fun ProductItemList.toWishlist(): WishlistItemRealm {
    return WishlistItemRealm().also {
        it.id = id
        it.name = name
        it.category = CategoryItemRealm().also { cat ->
            cat.id = category.id
            cat.name = category.name
            cat.description = category.description
        }
        it.discount = discount
        it.images = images
        it.price = price
        it.discount = discount
        it.rating = rating
        it.shortDescription = shortDescription
    }
}

fun WishlistItemRealm.toProductItem(): ProductItemList {
    return ProductItemList(
        id = id,
        name = name,
        shortDescription = shortDescription,
        category = ProductItemList.CategoryItem(
            id = category?.id ?: 0,
            name = category?.name.orEmpty(),
            description = category?.description.orEmpty()
        ),
        price = price,
        rating = rating,
        discount = discount,
        images = images
    )
}