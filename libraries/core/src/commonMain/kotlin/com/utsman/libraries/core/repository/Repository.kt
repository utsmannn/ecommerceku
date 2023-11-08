package com.utsman.libraries.core.repository

import com.utsman.libraries.core.state.Async
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

abstract class Repository {

    inline fun <reified T, U>(suspend () -> HttpResponse).reduce(
        crossinline block: (T) -> Async<U>
    ): Flow<Async<U>> {
        return flow {
            kotlinx.coroutines.delay(2000)
            val httpResponse = invoke()
            if (httpResponse.status.isSuccess()) {
                val data = httpResponse.body<T>()
                emit(block.invoke(data))
            } else {
                val throwable = Throwable(httpResponse.bodyAsText())
                emit(Async.Failure(throwable))
            }
        }.catch {
            val throwable = if (it is IOException) {
                Throwable("Device offline!")
            } else {
                it
            }
            emit(Async.Failure(throwable))
        }.onStart {
            emit(Async.Loading)
        }
    }
}