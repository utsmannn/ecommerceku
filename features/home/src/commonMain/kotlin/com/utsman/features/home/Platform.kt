package com.utsman.features.home

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform