package com.example.footballpj.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.gson.gson

object FootballApiClient {
    private const val API_KEY = "5b051d27a132d76f6d68dc3f122964d6"
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "v3.football.api-sports.io"
            }
            headers.append("x-apisports-key", API_KEY)
        }
    }
}
