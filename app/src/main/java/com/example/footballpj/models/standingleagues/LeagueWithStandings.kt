package com.example.footballpj.models.standingleagues

import kotlinx.serialization.Serializable

@Serializable
data class LeagueWithStandings(
    val standings: List<List<StandingTeam>>
)