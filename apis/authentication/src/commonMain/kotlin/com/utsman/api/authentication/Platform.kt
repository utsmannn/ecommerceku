package com.utsman.api.authentication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform