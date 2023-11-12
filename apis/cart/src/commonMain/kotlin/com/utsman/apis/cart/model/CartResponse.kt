package com.utsman.apis.cart.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: List<DataResponse?>?
) {
    @Serializable
    data class DataResponse(
        @SerialName("product_id")
        val productId: Int?,
        @SerialName("price")
        val price: Double?,
        @SerialName("discount")
        val discount: Int?,
        @SerialName("amount")
        val amount: Double?,
        @SerialName("quantity")
        val quantity: Int?,
        @SerialName("created_at")
        val createdAt: String?,
        @SerialName("updated_at")
        val updatedAt: String?
    )
}