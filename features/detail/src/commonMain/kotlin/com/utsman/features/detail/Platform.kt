package com.utsman.features.detail

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform