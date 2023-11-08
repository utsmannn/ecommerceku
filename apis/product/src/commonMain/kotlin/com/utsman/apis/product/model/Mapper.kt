package com.utsman.apis.product.model

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