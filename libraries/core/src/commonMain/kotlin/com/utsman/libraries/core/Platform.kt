package com.utsman.libraries.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform