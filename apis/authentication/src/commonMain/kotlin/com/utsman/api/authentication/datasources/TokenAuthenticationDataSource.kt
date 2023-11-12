package com.utsman.api.authentication.datasources

import com.utsman.libraries.core.network.TokenAuthentication

class TokenAuthenticationDataSource(
    private val keyValueDataSource: KeyValueDataSource
) : TokenAuthentication {

    override val getToken: String
        get() = keyValueDataSource.getString(KeyValueDataSource.KEY_TOKEN)
}