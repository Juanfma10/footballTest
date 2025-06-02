package com.example.footballpj.di

import android.content.Context
import com.example.footballpj.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json // ← importante
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Network {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "v3.football.api-sports.io"
                }
                headers.append("x-apisports-key", BuildConfig.API_KEY)
            }
        }
    }
}