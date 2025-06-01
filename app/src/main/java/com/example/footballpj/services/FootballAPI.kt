package com.example.footballpj.services
import com.example.footballpj.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.gson.gson



object FootballApiClient {
    val apiKey = BuildConfig.API_KEY
    val API_KEY = apiKey
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
