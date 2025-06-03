package com.example.footballpj.models.currentseason

import kotlinx.serialization.Serializable

@Serializable
data class LeagueCurrentSeasonResponse(
    val response: List<LeagueCurrentWrapper>
)