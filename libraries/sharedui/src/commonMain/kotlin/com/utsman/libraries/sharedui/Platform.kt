package com.utsman.libraries.sharedui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform