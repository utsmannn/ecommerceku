package com.utsman.features.user

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform