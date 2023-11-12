package com.utsman.api.authentication.datasources

import com.utsman.api.authentication.model.request.PostLogin
import com.utsman.api.authentication.model.request.PostRegister
import com.utsman.libraries.core.network.NetworkDataSources
import com.utsman.libraries.core.network.TokenAuthentication
import io.ktor.client.statement.HttpResponse

class AuthenticationNetworkDataSource(
    tokenAuthentication: TokenAuthentication
) : NetworkDataSources(BASE_URL, tokenAuthentication) {

    suspend fun postLogin(postLogin: PostLogin): HttpResponse {
        return postHttpResponse(LOGIN_ENDPOINT, postLogin)
    }

    suspend fun postRegister(postRegister: PostRegister): HttpResponse {
        return postHttpResponse(REGISTER_ENDPOINT, postRegister)
    }

    suspend fun getUser(): HttpResponse {
        return getHttpResponse(USER_ENDPOINT)
    }

    companion object {
        private const val BASE_URL = "https://marketfake.fly.dev"
        private const val LOGIN_ENDPOINT = "/auth/login"
        private const val REGISTER_ENDPOINT = "/auth/register"
        private const val USER_ENDPOINT = "/user"
    }
}