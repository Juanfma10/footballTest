package com.example.footballpj.models.teamInfo
import kotlinx.serialization.Serializable

@Serializable
data class TeamWrapper(
    val team: Team
)
