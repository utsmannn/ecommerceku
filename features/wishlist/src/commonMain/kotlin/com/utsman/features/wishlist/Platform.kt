package com.utsman.features.wishlist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform