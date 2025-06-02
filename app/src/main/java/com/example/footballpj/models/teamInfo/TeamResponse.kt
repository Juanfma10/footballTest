package com.example.footballpj.models.teamInfo

import kotlinx.serialization.Serializable


@Serializable
data class TeamResponse(
    val response: List<TeamWrapper>
)

