package com.utsman.apis.product.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: List<DataResponse?>?
) {
    @Serializable
    data class DataResponse(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("sort_description")
        val sortDescription: String?,
        @SerialName("category")
        val category: CategoryResponse?,
        @SerialName("price")
        val price: Double?,
        @SerialName("rating")
        val rating: Double?,
        @SerialName("discount")
        val discount: Int?,
        @SerialName("images")
        val images: String?
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
    }
}