package com.example.footballpj.models
import kotlinx.serialization.Serializable

@Serializable
data class TeamResponse(
    val response: List<TeamWrapper>
)

