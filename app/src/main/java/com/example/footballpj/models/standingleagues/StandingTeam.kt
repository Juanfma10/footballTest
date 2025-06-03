package com.example.footballpj.models.standingleagues

import kotlinx.serialization.Serializable

@Serializable
data class StandingTeam(
    val rank: Int,
    val team: TeamInfo,
    val points: Int
)