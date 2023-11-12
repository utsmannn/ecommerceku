package com.utsman.apis.cart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform