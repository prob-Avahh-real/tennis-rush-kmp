package com.tennisrush.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.encoding.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TennisRushHttpClient private constructor() {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true; encodeDefaults = true; coerceInputValues = true }

    val client = HttpClient {
        install(ContentNegotiation) { json(this@TennisRushHttpClient.json) }
        install(ContentEncoding) { gzip(); deflate() }
        defaultRequest { url("https://api.tennisrush.example.com/v1/"); contentType(ContentType.Application.Json) }
        install(HttpTimeout) { requestTimeoutMillis = 15_000; connectTimeoutMillis = 10_000; socketTimeoutMillis = 15_000 }
        install(HttpRequestRetry) { retryOnServerErrors(maxRetries = 2); retryOnException(maxRetries = 3); exponentialDelay() }
    }

    suspend inline fun <reified T> get(path: String, params: Map<String, String> = emptyMap()): T =
        client.get(path) { params.forEach { (k, v) -> parameter(k, v) } }.body()

    suspend fun warmup() { try { client.get("/health").body<Unit>() } catch (_: Exception) { } }

    companion object {
        val shared: TennisRushHttpClient by lazy { TennisRushHttpClient() }
    }
}
