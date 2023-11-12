package com.utsman.apis.product.model.entity

data class ProductItemList(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val category: CategoryItem,
    val price: Double,
    val rating: Double,
    val discount: Int,
    val images: String
) {
    data class CategoryItem(
        val id: Int,
        val name: String,
        val description: String
    )
}