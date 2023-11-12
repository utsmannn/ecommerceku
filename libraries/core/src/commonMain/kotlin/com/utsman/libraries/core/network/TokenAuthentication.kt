package com.utsman.libraries.core.network

import androidx.compose.runtime.compositionLocalOf

interface TokenAuthentication {

    val getToken : String

    companion object {

        val Empty = object : TokenAuthentication {
            override val getToken: String
                get() = ""
        }
    }
}

val LocalTokenAuthentication = compositionLocalOf<TokenAuthentication> { error("Token auth not provided!") }