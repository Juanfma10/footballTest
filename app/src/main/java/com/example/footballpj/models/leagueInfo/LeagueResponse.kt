package com.example.footballpj.models.leagueInfo

import kotlinx.serialization.Serializable

@Serializable
data class LeagueResponse(
    val response: List<LeagueWrapper>
)