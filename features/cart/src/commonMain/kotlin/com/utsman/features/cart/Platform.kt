package com.utsman.features.cart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform