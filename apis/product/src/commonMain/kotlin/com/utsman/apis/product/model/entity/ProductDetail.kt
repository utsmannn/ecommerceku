package com.utsman.apis.product.model.entity

data class ProductDetail(
    val id: Int,
    val name: String,
    val description: String,
    val category: ProductCategory,
    val price: Double,
    val rating: Double,
    val discount: Int,
    val images: List<String>,
    val userReview: List<ProductUserReview>
) {
    data class ProductCategory(
        val id: Int,
        val name: String,
        val description: String
    )

    data class ProductUserReview(
        val user: String,
        val review: String
    )
}