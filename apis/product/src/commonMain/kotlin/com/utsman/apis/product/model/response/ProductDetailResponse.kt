package com.utsman.apis.product.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: DataResponse?
) {
    @Serializable
    data class DataResponse(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("category")
        val category: CategoryResponse?,
        @SerialName("price")
        val price: Double?,
        @SerialName("rating")
        val rating: Double?,
        @SerialName("discount")
        val discount: Int?,
        @SerialName("images")
        val images: List<String?>?,
        @SerialName("user_review")
        val userReview: List<UserReviewResponse?>?
    ) {
        @Serializable
        data class CategoryResponse(
            @SerialName("id")
            val id: Int?,
            @SerialName("name")
            val name: String?,
            @SerialName("description")
            val description: String?
        )

        @Serializable
        data class UserReviewResponse(
            @SerialName("user")
            val user: String?,
            @SerialName("review")
            val review: String?
        )
    }
}