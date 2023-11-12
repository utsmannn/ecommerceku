package com.utsman.libraries.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.StringValues
import kotlinx.serialization.json.Json

abstract class NetworkDataSources(
    private val baseUrl: String,
    private val tokenAuthentication: TokenAuthentication = TokenAuthentication.Empty
) {

    suspend fun getHttpResponse(endPoint: String): HttpResponse {
        val url = "$baseUrl$endPoint"
        return client.get(url) {
            contentType(ContentType.Application.Json)
            if (tokenAuthentication.getToken.isNotEmpty()) {
                headers.append("Authorization", "Bearer ${tokenAuthentication.getToken}")
            }
        }
    }

    suspend fun postHttpResponse(endPoint: String, postData: Any? = null) : HttpResponse {
        val url = "$baseUrl$endPoint"
        return client.post(url) {
            contentType(ContentType.Application.Json)
            if (tokenAuthentication.getToken.isNotEmpty()) {
                headers.append("Authorization", "Bearer ${tokenAuthentication.getToken}")
            }
            setBody(postData)
        }
    }

    suspend fun multipartHttpResponse(endPoint: String, form: Map<String, String> = emptyMap()): HttpResponse {
        val url = "$baseUrl$endPoint"
        return client.post(url) {
            contentType(ContentType.Application.Json)
            if (tokenAuthentication.getToken.isNotEmpty()) {
                headers.append("Authorization", "Bearer ${tokenAuthentication.getToken}")
            }
            val stringValueData = StringValues.build {
                form.forEach { (key, value) ->
                    append(key, value)
                }
            }

            parameters {
                appendAll(stringValueData)
            }
        }
    }

    companion object {
        private val client: HttpClient by lazy {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                }
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY
                }
            }
        }
    }
}