package com.example.footballpj.models.standingleagues

import com.example.footballpj.models.teamInfo.Team
import kotlinx.serialization.Serializable

@Serializable
data class StandingTeam(
    val rank: Int,
    val team: Team,
    val points: Int
)