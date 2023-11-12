package com.utsman.api.authentication

import androidx.compose.runtime.compositionLocalOf
import com.utsman.api.authentication.datasources.AuthenticationNetworkDataSource
import com.utsman.api.authentication.datasources.KeyValueDataSource
import com.utsman.api.authentication.model.entity.User
import com.utsman.api.authentication.model.request.PostLogin
import com.utsman.api.authentication.model.request.PostRegister
import com.utsman.api.authentication.model.response.LoginResponse
import com.utsman.api.authentication.model.response.UserResponse
import com.utsman.api.authentication.model.toUser
import com.utsman.libraries.core.repository.Repository
import com.utsman.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val authenticationNetworkDataSource: AuthenticationNetworkDataSource,
    private val keyValueDataSource: KeyValueDataSource
) : Repository() {

    suspend fun login(user: String, password: String): Flow<Async<Unit>> {
        val postLogin = PostLogin(user, password)
        return suspend {
            authenticationNetworkDataSource.postLogin(postLogin)
        }.reduce<LoginResponse, Unit> { loginResponse ->
            if (loginResponse.data != null) {
                val token = loginResponse.data.token.orEmpty()
                keyValueDataSource.setString(KeyValueDataSource.KEY_TOKEN, token)
                Async.Success(Unit)
            } else {
                Async.Failure(Throwable(loginResponse.message.orEmpty()))
            }
        }
    }

    suspend fun register(user: String, password: String): Flow<Async<Unit>> {
        val postRegister = PostRegister(user, password)
        return suspend {
            authenticationNetworkDataSource.postRegister(postRegister)
        }.reduce<LoginResponse, Unit> { loginResponse ->
            if (loginResponse.status != false) { // cause data is null when success
                Async.Success(Unit)
            } else {
                Async.Failure(Throwable(loginResponse.message))
            }
        }
    }

    suspend fun getCurrentUser(): Flow<Async<User>> {
        return suspend {
            authenticationNetworkDataSource.getUser()
        }.reduce<UserResponse, User> { response ->
            val dataResponse = response.data
            if (dataResponse != null) {
                val user = dataResponse.toUser()
                Async.Success(user)
            } else {
                Async.Failure(Throwable(response.message))
            }
        }
    }

}

val LocalAuthenticationRepository = compositionLocalOf<AuthenticationRepository> { error("Auth repository not provided!") }