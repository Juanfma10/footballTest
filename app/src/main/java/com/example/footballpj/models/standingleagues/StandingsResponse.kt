package com.example.footballpj.models.standingleagues
import kotlinx.serialization.Serializable

@Serializable
data class StandingsResponse(
    val response: List<StandingLeagueWrapper>
)