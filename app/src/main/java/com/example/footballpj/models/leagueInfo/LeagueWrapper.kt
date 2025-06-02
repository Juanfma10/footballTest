package com.example.footballpj.models.leagueInfo

import kotlinx.serialization.Serializable

@Serializable
data class LeagueWrapper(
    val league: League
)