package com.plcoding.bookpedia.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(engine: HttpClientEngine) : HttpClient{
            return HttpClient(engine){
                install(ContentNegotiation){
                    json(
                        json = Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(HttpTimeout){
                    requestTimeoutMillis = 20_000
                    socketTimeoutMillis = 20_000
                }
                install(Logging){
                    logger = object  : Logger{
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                    level = LogLevel.ALL
                }
                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
    }
}